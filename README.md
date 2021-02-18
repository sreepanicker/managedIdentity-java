# User Assigned Managed Identity

This Java program demonstrates how to acquire a token from Azure AD tenant for Azure Key Vault, Azure App Config and Azure SQL DB. The virtual machine where the Java application runs is configured with a User assigned managed-identity. The same service principal is configured in Azure Key Vault, App Config and  SQL DB. 

This application also uses Azure Application Insight Java SDK and  instruments request, track dependencies, and collect performance counters, diagnose performance issues and exceptions. Application Insights Java 3.0+ standalone agent monitors application without any code changes.

**Steps to configure and runs the Java Application**

    1. Create a Create a User assigned managed identity (Search for Managed Identity in the Azure Portal)
    2. 
