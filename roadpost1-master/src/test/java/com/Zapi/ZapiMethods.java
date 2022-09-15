package com.Zapi;

import com.datamanager.ConfigManager;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.IClass;
import org.testng.ITestResult;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

public class ZapiMethods {
    static ConfigManager sys = new ConfigManager();
    static Logger log = LogManager.getLogger("ZapiMethods");
    JwtGeneratorClass sampleJwtGenerator = new JwtGeneratorClass();

    /** Status IDs enum */
    public enum Status {
        PASS(1), FAIL(2), WIP(3), BLOCKED(4);
        private final int value;

        Status(final int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /** URLS */
    private static final String BASE_URL = sys.getProperty("zephyr.BASE_URL");

    /** Zephyr Cloud credentials: format "username:password" / "username:apitoken"  or " " for none. */
    private static final String CREDENTIALS = sys.getProperty("zephyr.username")+":"+sys.getProperty("zephyr.password");

    // ================================================================================
    // ZAPI methods
    // ================================================================================
    /**
     * Gets the issueID, projectID for the project.
     *
     * @param testID
     * @return the issue ID for the specified test ID in the specified Project
     */
    public int[] getIssue_project_IDs(String testID){
        final HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        final CloseableHttpClient httpClient = clientBuilder.build();
        String ONSTART_URL = "https://"+sys.getProperty("zephyr.userUrl")+"/rest/api/2/issue/"+testID;
        final HttpGet httpGet =
                new HttpGet(ONSTART_URL);
        if (!CREDENTIALS.isEmpty()) {
            final String encoding = new Base64().encodeToString(CREDENTIALS.getBytes());
            httpGet.setHeader("Authorization", "Basic " + encoding);
        }
        else log.error("Credentials are empty");
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String JSON = null;
        try {
            JSON = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject result = new JSONObject(JSON);
        Integer issueID = result.getInt("id");
        JSONObject fields = result.getJSONObject("fields");
        JSONObject project = fields.getJSONObject("project");
        Integer projectID = project.getInt("id");
        int[] value = new int[2];
        value[0] = issueID;
        value[1] = projectID;
        return value;
    }
    /**
     * Gets the executionID for the project.
     *
     * @param requestType
     * @param projectID
     * @param issueID
     * @return the execution ID for the specified project ID and issue ID in the Project
     */
    public String getExecutionID(int projectID,int issueID,String requestType,String cycle){
        final HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        final CloseableHttpClient httpClient = clientBuilder.build();
        String ZAPI_URL = BASE_URL+ "/public/rest/api/1.0/executions?issueId="+issueID+"&projectId="+projectID;
        String jwtToken = null;
        jwtToken = sampleJwtGenerator.generateJWTToken(ZAPI_URL,requestType);
        final HttpGet httpGet = new HttpGet(ZAPI_URL);
        httpGet.setHeader("zapiAccessKey", sys.getProperty("zephyr.accessKey"));
        httpGet.setHeader("Authorization", jwtToken);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String JSON = null;
        try {
            JSON = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject result = new JSONObject(JSON);
        JSONArray executions_array = result.getJSONArray("executions");
        Integer indexVal = null;
        for(int i=0;i<executions_array.length();i++)
        {
            JSONObject executionIndex = executions_array.getJSONObject(i);
            JSONObject execution_loop = executionIndex.getJSONObject("execution");
            String cycleName = execution_loop.getString("cycleName");
            if(cycleName.equals(cycle))
            {
                indexVal = i;
                break;
            }
        }
        JSONObject cycleIndex = executions_array.getJSONObject(indexVal);
        JSONObject execution = cycleIndex.getJSONObject("execution");
        String executionID = execution.getString("id");
        return executionID;
    }
    /**
     * Update the specified test execution
     *
     * @param executionID
     * @param status
     */
    public void updateExecutionStatusinZephyr(String executionID,String requestType,Status status,int issueID,int projectID){
        final HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        final CloseableHttpClient httpClient = clientBuilder.build();
        String ZAPI_URL = BASE_URL+ "/public/rest/api/1.0/execution/"+executionID;
        String jwtToken = null;
        jwtToken = sampleJwtGenerator.generateJWTToken(ZAPI_URL,requestType);
        final HttpPut httpPut = new HttpPut(ZAPI_URL);
        httpPut.setHeader("zapiAccessKey", sys.getProperty("zephyr.accessKey"));
        httpPut.setHeader("Authorization", jwtToken);
        httpPut.setHeader("Accept", "application/json");
        httpPut.setHeader("Content-type", "application/json");
        String inputJson = "{\r\n    \"status\": {\r\n        \"id\":"+status.value+"\r\n    },\r\n    \"issueId\": "+issueID+",\r\n    \"projectId\": "+projectID+"\r\n}";
        log.info(inputJson);
        StringEntity stringEntity = null;
        try {
            stringEntity = new StringEntity(inputJson);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        httpPut.setEntity(stringEntity);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPut);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("Execution status updated for execution ID: "+executionID);
    }
    /**
     * Return the TestCycleName/TestID of specified test case name
     * @param result
     */
    public String returnTestAnnotationParameters(ITestResult result, int returnValue)
    {
        String TestID,CycleName,TestReturnVal=null;
        IClass obj = result.getTestClass();
        Class newobj = obj.getRealClass();
        Method[] testMethods = null;
        Method testMethod=null;
        try {
            testMethods=newobj.getMethods();
            for(Method method:testMethods){
                if(method.getName().equalsIgnoreCase(result.getName())){
                    testMethod=method;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (testMethod.isAnnotationPresent(ZephyrTestID.class))
        {
            ZephyrTestID useAsTestName = testMethod.getAnnotation(ZephyrTestID.class);
            if(returnValue == 1)
            {
                TestID = useAsTestName.TestID();
                TestReturnVal = TestID;
            }
            else if(returnValue == 2) {
                CycleName = useAsTestName.TestCycleName();
                TestReturnVal = CycleName;
            }
        }
        return TestReturnVal;
    }

}
