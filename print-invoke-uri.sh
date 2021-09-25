#!/bin/bash

source .env

FUNCTION_NAME="hello"

FUNCTION_CODE=$(az functionapp function keys list --name $TF_VAR_AZ_FUNCTION_NAME_APP --resource-group $TF_VAR_AZ_RESOURCE_GROUP --function-name $FUNCTION_NAME --query "default")
FUNCTION_CODE=$(echo "$FUNCTION_CODE" | tr -d '"') #Remove "" from result
FUNCTION_URL=$(az functionapp function show --function-name $FUNCTION_NAME --resource-group $TF_VAR_AZ_RESOURCE_GROUP --query "invokeUrlTemplate" --output tsv --name $TF_VAR_AZ_FUNCTION_NAME_APP)
FUNCTION_URL=$(echo $FUNCTION_URL|tr -d '\r\n') #Remove breaklines from result
INVOKE_FUNCTION="$FUNCTION_URL?code=$FUNCTION_CODE"

echo $INVOKE_FUNCTION
