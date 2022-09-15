## User settings
companyName="Zenq"
appName="seleniumjavaframework"
 


# It'll install <jre> to run your java program.
##sudo apt-get install default-jre

# It'll install <jdk> to build your java program.
##sudo apt-get install default-jdk
##apt-get -y install oracle-java8-installer

##Verify Java Version
java -version

##Set the JAVA_HOME variable
##export JAVA_HOME=/usr/lib/jvm/java-8-oracle

##chromedriver
#wget https://chromedriver.storage.googleapis.com/2.41/chromedriver_linux64.zip
#unzip chromedriver_linux64.zip
#sudo mv chromedriver /usr/bin/chromedriver
#sudo chown root:root /usr/bin/chromedriver
#sudo chmod +x /usr/bin/chromedriver
#rm chromedriver_linux64.zip

#ChromeDriver
sudo chown root:root Resources/Drivers/chromedriver
sudo chmod +x Resources/Drivers/chromedriver

##Install firefox
#sudo apt-get -y install firefox




#clean targets and Download the dependencies
mvn clean install 
 
 

