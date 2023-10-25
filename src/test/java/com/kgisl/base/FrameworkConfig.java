package com.kgisl.base;

import java.util.List;

import org.aeonbits.owner.Config;

@Config.Sources(value="file:${user.dir}/FrameworkConfig.properties")
public interface FrameworkConfig extends Config {
	
	@DefaultValue("Local")
	String RunType();
	String Browser();
	
	@DefaultValue("CholaMS")
	String Project();
	
	String Environment();
	
	String CreateTable();
	
	@DefaultValue("false")
	boolean Headless();
	
//	@DefaultValue("true")
	boolean DriverAutodownload();
	
	String MobileRuntype();
	
	String MobileAppPackageName();
	
	String MobileAppActivityName();
	
	String NodePath();
	
	String Appiumpath();	
	
	String EmailServer();
	 
	 String EmailUser();
	 
	 String EmailPassword();
	 
	 String MailPort();
	 
	 String Frommail();
	 
	 String Tomail();
	 
	 String MailSubject();
	 
	 String MailText();
	 
	String AppUrl();
	
	@DefaultValue("30")
	int SToWait1();
	
	@DefaultValue("30")
	int ImplictWait();
	    
	String DataSheetName();
	
	String UATAppUrl();
	
	List<String> Device();
	
	String Mode();
	
	String DirectPCcomprehensiveURL();

	String DirectPCOD();
	
	String DirectPCLiabilityURL();
	
	String DirectPCLongTermURL();
	
	String DirectTWComprehensive();
	
	String DirectTWOD();
	
	String DirectTWLongTerm();
	
	String SYMBO_PC_comprehensive();
	
	String SYMBO_PC_OD();
	
	String MUF_PC_comprehensive();
	
	String MUF_PC_OD();
	
	String ibl_cfd_PC_comprehensive();
	
	String ibl_cfd_PC_OD();

	String cifco_PC_comprehensive();
	
	String cifco_PC_OD();
	
	String au_PC_comprehensive();
	
	String au_PC_OD();
	
	String mut_PC_comprehensive();
	
	String mut_PC_OD();
	
	String equitas_PC_comprehensive();
	
	String equitas_PC_OD();
	
	String hlf_PC_comprehensive();
	
	String hlf_PC_OD();
	
	String bob_PC_comprehensive();
	
	String bob_PC_OD();
	
	String ubi_PC_comprehensive();
	
	String ubi_PC_OD();
	
	String ib_PC_comprehensive();
	
	String ib_PC_OD();
	
	String obc_PC_comprehensive();
	
	String obc_PC_OD();
	
	String CUMI_PC_comprehensive();
	
	String CUMI_PC_OD();
	
	String KDGOI_PC_comprehensive();
	
	String KDGOI_PC_OD();
	
	String Murugappa_PC_comprehensive();
	
	String Murugappa_PC_OD();
	
	String cqbk_PC_comprehensive();
	
	String cqbk_PC_OD();
	
	String AIB_PC_comprehensive();
	
	String AIB_PC_OD();
	
	String PNB_PC_comprehensive();
	
	String PNB_PC_OD();
	
	String MASH_PC_comprehensive();
	
	String MASH_PC_OD();
	
	String TMB_PC_comprehensive();
	
	String TMB_PC_OD();
	
	String MASH_PC_Liability();
	
	String DBname();
	
	String V4AppUrl();
	
	String CSC_PC_AppURL();
	
	String ADP_PC_AppURL();
	
	String INFINITYAppURL();
	
	String NeoCoreAppUrl();
	String BANCSAppURL();
	String CordysAppUrl();
	String SQAAppURL();
	String BreakinAppUrl();
	String V4MasterAppUrl();
	String SPAAAppUrl();
	String ZingAgentProAppUrl();
	String CRMappUrl();
	String E_policyAppUrl();
	String KioskAppUrl();    
	String CustomerClaimsPortalUrl();
	
	//Bancs UserName
	String Username1();	
	String Password1();
	String Username2();	
	String Password2();
	String Username3();	
	String Password3();
	String Username4();	
	String Password4();
	String Username5();	
	String Password5();
	String Username6();
	String Password6();
	String Username7();
	String Password7();
	
	String Neo_Username();	
	String Neo_Password();
	
	String Cordys_Username1();
	String Cordys_Password1();
	String Cordys_Username2();
	String Cordys_Password2();
	String Cordys_Username3();
	String Cordys_Password3();
	
	
	String BranchManager_user();
	String BranchManager_pass();

	String AreaHead_user();
	String AreaHead_pass();

	String StateHead_user();
	String StateHead_pass();

	String Geo_Head1_user();
	String Geo_Head1_pass();

	String GeoHead2_user();
	String Geo_Head2_pass();

	String PO_user();
	String PO_pass();

	String V4Master_user();
	String V4Master_pass();
	
	String ZingAgentPro_user(); 
	String ZingAgentPro_pass();
	
	String crm_user();
    String crm_pass();
    
    String KioskEpolicy_user();
    String KioskEpolicy_pass();
    
    String Cordys_initiator_Username();
    String Cordys_initiator_Password();
    String Cordys_Approver_Username();
    String Cordys_Approver_Password();
	
    String ExtractorAPI();
    String LiabilityIntegration();
    String SAODIntegration();
    String Comp_LongTerm_Integration();
    String CSC_ADP_V4_Integration();
     
	String BaseURI();
}
