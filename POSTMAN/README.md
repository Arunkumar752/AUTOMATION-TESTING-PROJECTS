Postman Java script functions which is kept commonly in collection and we can access in the prerequest anf request

  Different ways of handling request
        1)We have done the parameterizion of the data and sent the multiple request with sendNextRequest
              if (PolicyNos && PolicyNos.length > 0){
              postman.setNextRequest("PP_Get Fetch Schedule Combination");

        2)Storing request in collection and driving data from collection and convert it to local variable and sent the request

             const lv_temp_requestbody=JSON.parse(pm.collectionVariables.get("cv_temp_Variable01"));
             pm.variables.set("lv_requestbody",JSON.stringify(lv_temp_requestbody));

        3)Worked in different variables
               1)Environmental variable
               2)Global variable
               3)Collection variable
               4)local variable
               5)Data variable
