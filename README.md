# User Assigned Managed Identity

This Java program demonstrates how to acquire a token from Azure AD tenant for Azure Key Vault, Azure App Config and Azure SQL DB. The virtual machine where the Java application runs is configured with a User assigned managed-identity. The same service principal is configured in Azure Key Vault, App Config and  SQL DB. 

Application also uses Azure Application Insight Java SDK and  instruments request, track dependencies, and collect performance counters, diagnose performance issues and exceptions. Application Insights Java 3.0+ standalone agent monitors application without any code changes.

**Steps to configure and runs the Java Application**

    1. Create a User assigned managed identity (Search for Managed Identity in the Azure Portal)
    2.  Provision a Virtual Machine in Azure 
    3.  Set User assigned Managed identity to Virtual Machine
    4.  Provision an Azure App Config
    5.  Set User assigned managed identity to Azure App Config
    6.  Assign permission  to User managed identity for accessing Azure App Config
    7.  Create a key in Azure App Config for the application; we store the Azure key vault URL in this key
    8.  Provision Azure Key Vault
    9.  Assign permission to User managed identity for accessing Azure Key Vault
    10. Create a Secret in Azure Key vault; we store the SQL DB server URL in this secret 
    11. Provision a SQL DB
    12. Create a database 
    13. Create a contained user in the database and execute the following commands.
        a. Create User [User-assigned managed identity] FROM EXTERNAL PROVIDER
        b. ALTER ROLE db_datareader ADD MEMBER [User-assigned managed identity] 
        c. ALTER ROLE db_datawriter ADD MEMBER [User-assigned managed identity] 
        To execute these commands, the User needs to be an admin in SQL DB and an Azure AD tenant user.
    14. Set firewall rules in SQL DB

If you are interested, you can create an Azure application insight and ingest application trace details into Application insight. 
You can use the following configuration details. Please change the instrumentation key to an appropriate one. 

applicationinsight.json
{
   "connectionString": "InstrumentationKey=XXXXX", 
   "instrumentation": {
    "logging": {
      "level": "TRACE"
    }
  }
}

Application uses java.util.logging package for logging. 

**How to run the Java Application**

java -javaagent:applicationinsights-agent-3.0.2.jar -jar UserManagedIdentity-1.0-SNAPSHOT-jar-with-dependencies.jar

   
    
