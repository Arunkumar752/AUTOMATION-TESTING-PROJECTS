package com.kgisl.library;

import java.util.List;

public class ReportOut {

	// SPAA
	private String SPPA_requestID;

	public String getSPPA_requestID() {
		return SPPA_requestID;
	}

	public void setSPPA_requestID(String sPPA_requestID) {
		SPPA_requestID = sPPA_requestID;
	}

	private String pheonixQuoteNumber;
	private String Appname;
	private String proposalNo;
	private String policyNo;
	private String premiumAmount;
	private String GSTAmount;
	private String TotalAmount;
	private String V4Quotenumber;
	private String pheonixMobileNumber;
	private String V4RegistrationNo;
	private String RegisterNumber;
	private String ApplicationName;
	
	//customer claims portal
	private String DateOfLoss=null;

	// BANCS
	private String EngineNumber = null;
	private String ChassisNumber = null;
	private String AppReferenceNumber = null;
	private String PolicyNumber = "null";
	private String ProductName = null;
	private String PolicyHolder = null;
	private String PolicyHolderCode = null;
	private String RequestNo1 = null;
	private String RequestNo2 = null;
	private String QuotationAndPolicy = null;
	private String EffectiveDate = null;
	private String RegistrationNumber = null;
	private String ReceiptNumber = null;
	private String EndorsementNumber = null;
	private String PolicyExpiryDate = null;
	private String PolicyInceptionDate = null;
	private String PolicyProduct = null;
	private String ModeCode = null;
	private String ProductCode = null;
	public String emailContent = null;
	public String RenewedProposalNumber=null;
	
	public String getRenewedProposalNumber() {
		return RenewedProposalNumber;
	}

	public void setRenewedProposalNumber(String renewedProposalNumber) {
		RenewedProposalNumber = renewedProposalNumber;
	}

	//CRM
    private String AmountClaimed = null;
    private String CaimStatus = null;
    private String claimStatus = null;
    private String policyNumber = null;
    private String Type = null;
    private String PolicyStatus=null;
    private String PolicyType=null;
    private String Age_of_Policy=null;
    private String Customer_Id=null;
    private String Customer=null;
    private String RSD=null;
    private String RED=null;
    private String PolicyApprovedDate=null;
    private String Veh_regNumber = null;
    private String Chasis_No=null;
    private String Engine_No=null;
    private String Age_of_Vehicle=null;
    private String Product_Name=null;
    private String Product_LOB=null;
    private String Product_Category=null;
    private String Proposal_Number =null;
    private String ProposalDate = null;
    private String TransactionDate=null;
    private String NCB_Percentage = null;
    private String Premium= null;
    private String SI_or_IDV=null;
    private String Segment= null;
    private String Division=null;
    private String No_of_Claims=null;
    private String Total_Claimed_amount=null;
    private String Intermediary_Name=null;
    private String Intermediary_Category_Name=null;
    private String Intermediary_Mobile=null;
    private String Intermediary_Code=null;
    private String Intermediary_Email=null;
    private String PAN=null;
    private String Aadhar_Card=null;
    private String DrivingLicenceNumber=null;
    private String Gender=null;
    private String DateofBirth=null;
    private String KYC_DocumentNumber=null;
    private String CustomerOwner=null;
    private String claimNumber = null;
    private String mobilenumber=null;
    private String phonenumber=null;
    private String Proposal_number = null;
    private String Nature_Of_Loss = null;
    private String Claimedamount = null;
    private String NatureOfLoss = null;
    
  //Cordys
    private String cordysReferenceNo;
    private String PendingLevel;
    
    
    //phoenix
    private String PhoenixDateFormat;
    private String PhoenixHoldername;
    
    public String getNatureOfLoss() {
		return NatureOfLoss;
	}

	public void setNatureOfLoss(String natureOfLoss) {
		NatureOfLoss = natureOfLoss;
	}

	public String getNature_Of_Loss() {
		return Nature_Of_Loss;
	}

	public void setNature_Of_Loss(String nature_Of_Loss) {
		Nature_Of_Loss = nature_Of_Loss;
	}

	public String getClaimedamount() {
		return Claimedamount;
	}

	public void setClaimedamount(String claimedamount) {
		Claimedamount = claimedamount;
	}

	private String Phone=null;
    private String Mobile = null;
    
    private String Plan_Name = null;
    private String Claim_Status=null;
    private String Policy_Period_From=null;
    private String Policy_period_To = null;
    private String Insured_Name = null;
    
    public String getPlan_Name() {
		return Plan_Name;
	}

	public void setPlan_Name(String plan_Name) {
		Plan_Name = plan_Name;
	}

	public String getClaim_Status() {
		return Claim_Status;
	}

	public void setClaim_Status(String claim_Status) {
		Claim_Status = claim_Status;
	}

	public String getPolicy_Period_From() {
		return Policy_Period_From;
	}

	public void setPolicy_Period_From(String policy_Period_From) {
		Policy_Period_From = policy_Period_From;
	}

	public String getPolicy_period_To() {
		return Policy_period_To;
	}

	public void setPolicy_period_To(String policy_period_To) {
		Policy_period_To = policy_period_To;
	}

	public String getInsured_Name() {
		return Insured_Name;
	}

	public void setInsured_Name(String insured_Name) {
		Insured_Name = insured_Name;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public String getMobile() {
		return Mobile;
	}

	public void setMobile(String mobile) {
		Mobile = mobile;
	}

	public String getPheonixMobileNumber() {
		return pheonixMobileNumber;
	}

	public void setPheonixMobileNumber(String pheonixMobileNumber) {
		this.pheonixMobileNumber = pheonixMobileNumber;
	}

	public String getV4RegistrationNo() {
		return V4RegistrationNo;
	}

	public void setV4RegistrationNo(String v4RegistrationNo) {
		V4RegistrationNo = v4RegistrationNo;
	}

	public String getAmountClaimed() {
		return AmountClaimed;
	}

	public void setAmountClaimed(String amountClaimed) {
		AmountClaimed = amountClaimed;
	}

	public String getCaimStatus() {
		return CaimStatus;
	}

	public void setCaimStatus(String caimStatus) {
		CaimStatus = caimStatus;
	}

	public String getClaimStatus() {
		return claimStatus;
	}

	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getPolicyStatus() {
		return PolicyStatus;
	}

	public void setPolicyStatus(String policyStatus) {
		PolicyStatus = policyStatus;
	}

	public String getPolicyType() {
		return PolicyType;
	}

	public void setPolicyType(String policyType) {
		PolicyType = policyType;
	}

	public String getAge_of_Policy() {
		return Age_of_Policy;
	}

	public void setAge_of_Policy(String age_of_Policy) {
		Age_of_Policy = age_of_Policy;
	}

	public String getCustomer_Id() {
		return Customer_Id;
	}

	public void setCustomer_Id(String customer_Id) {
		Customer_Id = customer_Id;
	}

	public String getCustomer() {
		return Customer;
	}

	public void setCustomer(String customer) {
		Customer = customer;
	}

	public String getRSD() {
		return RSD;
	}

	public void setRSD(String rSD) {
		RSD = rSD;
	}

	public String getRED() {
		return RED;
	}

	public void setRED(String rED) {
		RED = rED;
	}

	public String getPolicyApprovedDate() {
		return PolicyApprovedDate;
	}

	public void setPolicyApprovedDate(String policyApprovedDate) {
		PolicyApprovedDate = policyApprovedDate;
	}

	public String getVeh_regNumber() {
		return Veh_regNumber;
	}

	public void setVeh_regNumber(String veh_regNumber) {
		Veh_regNumber = veh_regNumber;
	}

	public String getChasis_No() {
		return Chasis_No;
	}

	public void setChasis_No(String chasis_No) {
		Chasis_No = chasis_No;
	}

	public String getEngine_No() {
		return Engine_No;
	}

	public void setEngine_No(String engine_No) {
		Engine_No = engine_No;
	}

	public String getAge_of_Vehicle() {
		return Age_of_Vehicle;
	}

	public void setAge_of_Vehicle(String age_of_Vehicle) {
		Age_of_Vehicle = age_of_Vehicle;
	}

	public String getProduct_Name() {
		return Product_Name;
	}

	public void setProduct_Name(String product_Name) {
		Product_Name = product_Name;
	}

	public String getProduct_LOB() {
		return Product_LOB;
	}

	public void setProduct_LOB(String product_LOB) {
		Product_LOB = product_LOB;
	}

	public String getProduct_Category() {
		return Product_Category;
	}

	public void setProduct_Category(String product_Category) {
		Product_Category = product_Category;
	}

	public String getProposal_Number() {
		return Proposal_Number;
	}

	public void setProposal_Number(String proposal_Number) {
		Proposal_Number = proposal_Number;
	}

	public String getProposalDate() {
		return ProposalDate;
	}

	public void setProposalDate(String proposalDate) {
		ProposalDate = proposalDate;
	}

	public String getTransactionDate() {
		return TransactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		TransactionDate = transactionDate;
	}

	public String getNCB_Percentage() {
		return NCB_Percentage;
	}

	public void setNCB_Percentage(String nCB_Percentage) {
		NCB_Percentage = nCB_Percentage;
	}

	public String getPremium() {
		return Premium;
	}

	public void setPremium(String premium) {
		Premium = premium;
	}

	public String getSI_or_IDV() {
		return SI_or_IDV;
	}

	public void setSI_or_IDV(String sI_or_IDV) {
		SI_or_IDV = sI_or_IDV;
	}

	public String getSegment() {
		return Segment;
	}

	public void setSegment(String segment) {
		Segment = segment;
	}

	public String getDivision() {
		return Division;
	}

	public void setDivision(String division) {
		Division = division;
	}

	public String getNo_of_Claims() {
		return No_of_Claims;
	}

	public void setNo_of_Claims(String no_of_Claims) {
		No_of_Claims = no_of_Claims;
	}

	public String getTotal_Claimed_amount() {
		return Total_Claimed_amount;
	}

	public void setTotal_Claimed_amount(String total_Claimed_amount) {
		Total_Claimed_amount = total_Claimed_amount;
	}

	public String getIntermediary_Name() {
		return Intermediary_Name;
	}

	public void setIntermediary_Name(String intermediary_Name) {
		Intermediary_Name = intermediary_Name;
	}

	public String getIntermediary_Category_Name() {
		return Intermediary_Category_Name;
	}

	public void setIntermediary_Category_Name(String intermediary_Category_Name) {
		Intermediary_Category_Name = intermediary_Category_Name;
	}

	public String getIntermediary_Mobile() {
		return Intermediary_Mobile;
	}

	public void setIntermediary_Mobile(String intermediary_Mobile) {
		Intermediary_Mobile = intermediary_Mobile;
	}

	public String getIntermediary_Code() {
		return Intermediary_Code;
	}

	public void setIntermediary_Code(String intermediary_Code) {
		Intermediary_Code = intermediary_Code;
	}

	public String getIntermediary_Email() {
		return Intermediary_Email;
	}

	public void setIntermediary_Email(String intermediary_Email) {
		Intermediary_Email = intermediary_Email;
	}

	public String getPAN() {
		return PAN;
	}

	public void setPAN(String pAN) {
		PAN = pAN;
	}

	public String getAadhar_Card() {
		return Aadhar_Card;
	}

	public void setAadhar_Card(String aadhar_Card) {
		Aadhar_Card = aadhar_Card;
	}

	public String getDrivingLicenceNumber() {
		return DrivingLicenceNumber;
	}

	public void setDrivingLicenceNumber(String drivingLicenceNumber) {
		DrivingLicenceNumber = drivingLicenceNumber;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public String getDateofBirth() {
		return DateofBirth;
	}

	public void setDateofBirth(String dateofBirth) {
		DateofBirth = dateofBirth;
	}

	public String getKYC_DocumentNumber() {
		return KYC_DocumentNumber;
	}

	public void setKYC_DocumentNumber(String kYC_DocumentNumber) {
		KYC_DocumentNumber = kYC_DocumentNumber;
	}

	public String getCustomerOwner() {
		return CustomerOwner;
	}

	public void setCustomerOwner(String customerOwner) {
		CustomerOwner = customerOwner;
	}

	public String getClaimNumber() {
		return claimNumber;
	}

	public void setClaimNumber(String claimNumber) {
		this.claimNumber = claimNumber;
	}

	public String getMobilenumber() {
		return mobilenumber;
	}

	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getProposal_number() {
		return Proposal_number;
	}

	public void setProposal_number(String proposal_number) {
		Proposal_number = proposal_number;
	}

	public String getTotal_Liability_PaidAmount() {
		return Total_Liability_PaidAmount;
	}

	public void setTotal_Liability_PaidAmount(String total_Liability_PaidAmount) {
		Total_Liability_PaidAmount = total_Liability_PaidAmount;
	}

	public String getRegistration_Number() {
		return Registration_Number;
	}

	public void setRegistration_Number(String registration_Number) {
		Registration_Number = registration_Number;
	}

	public String getEngine_Number() {
		return Engine_Number;
	}

	public void setEngine_Number(String engine_Number) {
		Engine_Number = engine_Number;
	}

	public String getChassis_Number() {
		return Chassis_Number;
	}

	public void setChassis_Number(String chassis_Number) {
		Chassis_Number = chassis_Number;
	}

	public String getInsured_ID() {
		return Insured_ID;
	}

	public void setInsured_ID(String insured_ID) {
		Insured_ID = insured_ID;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getAadhar_card() {
		return Aadhar_card;
	}

	public void setAadhar_card(String aadhar_card) {
		Aadhar_card = aadhar_card;
	}

	public String getPolicy_Number() {
		return policy_Number;
	}

	public void setPolicy_Number(String policy_Number) {
		this.policy_Number = policy_Number;
	}

	private String Total_Liability_PaidAmount = null;
    private String Registration_Number=null;
    private String Engine_Number = null;
    private String Chassis_Number = null;
    private String Insured_ID=null;
    private String pan = null;
    private String Aadhar_card= null;
    private String policy_Number=null;

	public String getEmailContent() {
		return emailContent;
	}

	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}
	
		public String getProductCode() {
		return ProductCode;
	}

	public void setProductCode(String productCode) {
		ProductCode = productCode;
	}

	public String getModeCode() {
		return ModeCode;
	}

	public void setModeCode(String modeCode) {
		ModeCode = modeCode;
	}


	public String getPolicyProduct() {
		return PolicyProduct;
	}

	public void setPolicyProduct(String policyProduct) {
		PolicyProduct = policyProduct;
	}

	// NEOCORE
	private String PremiumValue = null;
	private String neo_PolicyNumber = null;
	private String ProposalNumber = null;
	private String HolderCode = null;

	public String getProposalNumber() {
		return ProposalNumber;
	}

	public void setProposalNumber(String proposalNumber) {
		ProposalNumber = proposalNumber;
	}

	public String getHolderCode() {
		return HolderCode;
	}

	public void setPremiumValue(String premiumValue) {
		PremiumValue = premiumValue;
	}

	public String getNeo_PolicyNumber() {
		return neo_PolicyNumber;
	}

	public void setNeo_PolicyNumber(String neo_PolicyNumber) {
		this.neo_PolicyNumber = neo_PolicyNumber;
	}

	public String getPremiumValue() {
		return PremiumValue;
	}

	public String getPolicyExpiryDate() {
		return PolicyExpiryDate;
	}

	public void setPolicyExpiryDate(String policyExpiryDate) {
		PolicyExpiryDate = policyExpiryDate;
	}

	public String getRequestNo1() {
		return RequestNo1;
	}

	public void setRequestNo1(String requestNo1) {
		RequestNo1 = requestNo1;
	}

	public String getRequestNo2() {
		return RequestNo2;
	}

	public void setRequestNo2(String requestNo2) {
		RequestNo2 = requestNo2;
	}

	public void setHolderCode(String holderCode) {
		HolderCode = holderCode;
	}

	public String getPolicyHolderCode() {
		return PolicyHolderCode;
	}

	public void setPolicyHolderCode(String policyHolderCode) {
		PolicyHolderCode = policyHolderCode;
	}

	public String getEndorsementNumber() {
		return EndorsementNumber;
	}

	public void setEndorsementNumber(String endorsementNumber) {
		EndorsementNumber = endorsementNumber;
	}

	public String getReceiptNumber() {
		return ReceiptNumber;
	}

	public void setReceiptNumber(String receiptNumber) {
		ReceiptNumber = receiptNumber;
	}

	public String getRegistrationNumber() {
		return RegistrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		RegistrationNumber = registrationNumber;
	}

	public String getEffectiveDate() {
		return EffectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		EffectiveDate = effectiveDate;
	}

	public String getQuotationAndPolicy() {
		return QuotationAndPolicy;
	}

	public void setQuotationAndPolicy(String policyNumber_Info) {
		this.QuotationAndPolicy = policyNumber_Info;
	}

	public String getProductName() {
		return ProductName;
	}

	public void setProductName(String productName) {
		ProductName = productName;
	}

	public String getPolicyHolder() {
		return PolicyHolder;
	}

	public void setPolicyHolder(String policyHolder) {
		PolicyHolder = policyHolder;
	}

	public String getPolicyNumber() {
		return PolicyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		PolicyNumber = policyNumber;
	}

	public String getAppReferenceNumber() {
		return AppReferenceNumber;
	}

	public void setAppReferenceNumber(String appReferenceNumber) {
		AppReferenceNumber = appReferenceNumber;
	}

	public String getChassisNumber() {
		return ChassisNumber;
	}

	public void setChassisNumber(String chassisNumber) {
		ChassisNumber = chassisNumber;
	}

	public String getEngineNumber() {
		return EngineNumber;
	}

	public void setEngineNumber(String engineNumber) {
		EngineNumber = engineNumber;
	}

	// Phoenix

	public String getPheonixQuoteNumber() {
		return pheonixQuoteNumber;
	}

	public void setPheonixQuoteNumber(String pheonixQuoteNumber) {
		this.pheonixQuoteNumber = pheonixQuoteNumber;
	}

	public String getAppname() {
		return Appname;
	}

	public void setAppname(String appname) {
		Appname = appname;
	}

	public String getProposalNo() {
		return proposalNo;
	}

	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getPremiumAmount() {
		return premiumAmount;
	}

	public void setPremiumAmount(String premiumAmount) {
		this.premiumAmount = premiumAmount;
	}

	public String getGSTAmount() {
		return GSTAmount;
	}

	public void setGSTAmount(String gSTAmount) {
		GSTAmount = gSTAmount;
	}

	public String getTotalAmount() {
		return TotalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		TotalAmount = totalAmount;
	}

	public String getV4Quotenumber() {
		return V4Quotenumber;
	}

	public void setV4Quotenumber(String v4Quotenumber) {
		V4Quotenumber = v4Quotenumber;
	}

	public String getPolicyInceptionDate() {
		return PolicyInceptionDate;
	}

	public void setPolicyInceptionDate(String policyInceptionDate) {
		PolicyInceptionDate = policyInceptionDate;
	}

	public String getMobileNumber() {
		return pheonixMobileNumber;
	}

	public void setMobileNumber(String pheonixMobileNumber) {
		this.pheonixMobileNumber = pheonixMobileNumber;
	}

	public String getVehicleRegNo() {
		return V4RegistrationNo;
	}

	public void setVehicleRegNo(String v4RegistrationNo) {
		V4RegistrationNo = v4RegistrationNo;
	}

	public String getRegisterNumber() {
		return RegisterNumber;
	}

	public void setRegisterNumber(String registerNumber) {
		RegisterNumber = registerNumber;
	}

	public String getApplicationName() {
		return ApplicationName;
	}

	public void setApplicationName(String applicationName) {
		ApplicationName = applicationName;
	}

	public String getCordysReferenceNo() {
		return cordysReferenceNo;
	}

	public void setCordysReferenceNo(String cordysReferenceNo) {
		this.cordysReferenceNo = cordysReferenceNo;
	}

	public String getPendingLevel() {
		return PendingLevel;
	}

	public void setPendingLevel(String pendingLevel) {
		PendingLevel = pendingLevel;
	}

	public String getPhoenixDateFormat() {
		return PhoenixDateFormat;
	}

	public void setPhoenixDateFormat(String phoenixDateFormat) {
		PhoenixDateFormat = phoenixDateFormat;
	}

	public String getDateOfLoss() {
		return DateOfLoss;
	}

	public void setDateOfLoss(String dateOfLoss) {
		DateOfLoss = dateOfLoss;
	}

	public String getPhoenixHoldername() {
		return PhoenixHoldername;
	}

	public void setPhoenixHoldername(String phoenixHoldername) {
		PhoenixHoldername = phoenixHoldername;
	}

}
