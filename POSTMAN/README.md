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

        4)Worked in Authentications like like basicAuth,Oath 1,Oath2,bearer token ,Authrization in header

        5)Verified whole response body,validating particular field and value,validating field null,Validated length of object ,removed dynamic tags or validated with 2 response body 
          ,verifying response status code,Validated pdf string null,Validated XML Response
                  pm.test("Verfied "+desc+" - "+value, ()=> {
              var responseData = pm.response.text();
              const Res = cheerio.load(responseData, {
              ignoreWhitespace: true,
              xmlMode: true
              });
              let DocType= Res(Object).find(field);
              pm.expect(DocType.text()).to.eql(value);
      6)Javascrpt function which been developed to reduce the code reusuablity,which is kept in collection and called as map testUtils.ValidateKeyValue();

                      testUtils = {
            
                ValidateKeyValue: function (RespObj,SearchKeyVal,Expectedvalue){
                const KeyList=Object.getOwnPropertyNames(RespObj);
                var value = null;
                for(let Key in KeyList){
                    if(RespObj[KeyList[Key]]&& typeof RespObj[KeyList[Key]]==='object'&& SearchKeyVal != KeyList[Key]){
                        console.log("It is Object");
                        Value = testUtils.ValidateKeyValue(RespObj[KeyList[Key]],SearchKeyVal,Expectedvalue);
            
                    }
                    if(KeyList[Key]==(SearchKeyVal)){
                        Value=RespObj[KeyList[Key]];
                        if(typeof Value === 'object'){
                        var Result = (JSON.stringify(Value) === JSON.stringify(Expectedvalue)) ? true : false;
                        }
            
                        else{
            
                        var Result = (Value === Expectedvalue) ? true : false; 
                        }
                        return Result
                        //  console.log(Result)
                        break;
                    }
                }
            },
            
                GetvalueFromObject: function (pm,RespObj,SearchKeyVal){
                const KeyList=Object.getOwnPropertyNames(RespObj);
                var value = null;
                for(let Key in KeyList){
                    if(RespObj[KeyList[Key]]&& typeof RespObj[KeyList[Key]]==='object'&& SearchKeyVal != KeyList[Key]){
                        Value = testUtils.GetvalueFromObject(RespObj[KeyList[Key]],SearchKeyVal);
            
                    }
            
                    if(KeyList[Key]==(SearchKeyVal)){
                        Value=RespObj[KeyList[Key]];
                        return value
                        //  console.log(JSON.stringify(Value))
                        break;
                    }
                }
            },
            
            
                UpdateDate: function (val,Type) {
                
                if(Type=="D"){
                    const tm = new Date();
                    tm.setDate(new Date().getDate() + (val))
                    // console.log(tm)
                    return tm.toLocaleDateString('en-GB')
            
                }
                else if(Type=="M"){
                    const tm = new Date();
                    tm.setMonth(new Date().getMonth() + (val))
                    // console.log(tm)
                    return tm.toLocaleDateString('en-GB')
            
                }
                    else if(Type=="Y"){
                        const tm = new Date();
                        tm.setFullYear(new Date().getFullYear() + (val))
                        // console.log(tm)
                    return tm.toLocaleDateString('en-GB')
                
                    }    
            
            
            },
            
                GetObjectLength: function (RespObj,SearchKeyVal){
                const KeyList=Object.getOwnPropertyNames(RespObj);
                var value = null;
                for(let Key in KeyList){
                    if(RespObj[KeyList[Key]]&& typeof RespObj[KeyList[Key]]==='object'&& SearchKeyVal != KeyList[Key]){
                        console.log("It is Object");
                        Value = GetObjectLength(RespObj[KeyList[Key]],SearchKeyVal,Expectedvalue);
                    }
            
                    if(KeyList[Key]==(SearchKeyVal)){
                        Value=RespObj[KeyList[Key]];
                        if(typeof Value === 'object'){
                       var Objlen = Object.keys(Value).length;
            
                        }
                        else{
                        var Objlen = 0;
            
                        }
                        //  console.log(Objlen)
                        return Objlen
                        // break;
                    }
                }
            },
            
            
            
            
               removeProperty: function (object, propToDelete) {
                    Object
                        .keys(object)
                        .forEach(key => {
                            const val = object[key];
            
                            // If dealing with an object, removeProperty in it.
                            if (val && typeof val === 'object') {
                                testUtils.removeProperty(val);
                            }
            
                            // If deleteable, delete and return
                            if (key === propToDelete) {
                                delete object[key];
                                return object;
                            }
                        });
                    return object;
                },
                
                 //method to validate/verify response status code is 200 (OK)
                verfiyResponseSuccessStatusCode: function (pm, description) {
                    pm.test("Verfied "+description+" - Success -200", () => {
                        pm.response.to.have.status(200);
                        pm.response.to.have.jsonBody;
                    });
                },
                
                 VerifyXMLFieldAndValueInIBL: function(pm,Object,field,value,desc){
                    pm.test("Verfied "+desc+" - "+value, ()=> {
                        var responseData = pm.response.text();
                        const Res = cheerio.load(responseData, {
                        ignoreWhitespace: true,
                        xmlMode: true
                        });
                        let DocType= Res(Object).find(field);
                        console.log(DocType.text())
                        pm.expect(DocType.text()).to.eql(value);
                    }
                    );},
            
                      FetchXMLFieldAndValue: function(pm,Object,field){
                        var responseData = pm.response.text();
                        const Res = cheerio.load(responseData, {
                        ignoreWhitespace: true,
                        xmlMode: true
                        });
                        let DocType= Res(Object).find(field);
                        // console.log(DocType.text())   
                         return DocType.text();
                    },
                   
                           },
                                verifyStatusMessage: function (pm, Status, description) {
                    pm.test(description, () => {
                        const Actresponse = pm.response.json();
                        if( Actresponse.hasOwnProperty("status")==true){           
                        pm.expect(Status).is.to.include(Actresponse.status);
                        }else{
                            pm.expect.fail("No Error Message found.");
                        }
                    })
                 } , generateString: function(length) {
                
                const characters ='ABCDEFGHIJKLMNOPQ0123456789123456789';
                let result = '';
                const charactersLength = characters.length;
                for ( let i = 0; i < length; i++ ) {
                    result += characters.charAt(Math.floor(Math.random() * charactersLength));
                }
            
                return result;
            },
             verifyFieldNullOrNot : function (pm,description) {        
                       pm.test("Verfied "+description, ()=> {
                        const jsonData = pm.response.json()
                        if(jsonData.returnPDF == null)
                        {
                            pm.expect.fail(jsonData.returnPDF)
                        }
                        else{
                            pm.expect(jsonData.returnPDF).not.equal(null)
                        }
                });},
            
            const  FutureDate= testUtils.UpdateDate(10,"D")
            pm.collectionVariables.set("cv_FutureDate",FutureDate);
            const PreviousDtae = testUtils.UpdateDate(-10,"D")
            pm.collectionVariables.set("cv_PreviousDate",PreviousDtae);
            const currentDate = testUtils.UpdateDate(0,"D")
            pm.collectionVariables.set("cv_CurrentDate", currentDate);
