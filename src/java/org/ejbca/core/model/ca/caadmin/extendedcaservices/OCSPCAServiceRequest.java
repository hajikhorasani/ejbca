/*************************************************************************
 *                                                                       *
 *  EJBCA: The OpenSource Certificate Authority                          *
 *                                                                       *
 *  This software is free software; you can redistribute it and/or       *
 *  modify it under the terms of the GNU Lesser General Public           *
 *  License as published by the Free Software Foundation; either         *
 *  version 2.1 of the License, or any later version.                    *
 *                                                                       *
 *  See terms of license at gnu.org.                                     *
 *                                                                       *
 *************************************************************************/
 
package org.ejbca.core.model.ca.caadmin.extendedcaservices;

import java.io.Serializable;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.cert.ocsp.OCSPReq;
import org.cesecore.certificates.ca.extendedservices.ExtendedCAServiceRequest;
import org.cesecore.config.OcspConfiguration;
import org.ejbca.core.protocol.ocsp.OCSPResponseItem;

/**
 * Class used when requesting OCSP related services from a CA.  
 *
 * @version $Id$
 */
public class OCSPCAServiceRequest extends ExtendedCAServiceRequest implements Serializable {    
    
	private static final long serialVersionUID = 8154162667035332666L;

    public static final Logger m_log = Logger.getLogger(OCSPCAServiceRequest.class);
	
    private OCSPReq req = null;
    private ArrayList<OCSPResponseItem> responseList = null;
    private Extensions exts = null;
    private String sigAlg = "SHA1WithRSA";
    private boolean includeChain = true;
    private String privKeyProvider = "BC"; // Default for OCSP responder not using the CAs private key
    private int respIdType = OcspConfiguration.RESPONDERIDTYPE_KEYHASH; // Default to use KeyId
    
    // Parameters that are used when we use the CAs private key to sign responses
    private PrivateKey privKey = null;
    private List<Certificate> certificateChain = null;
    
    /** Constructor for OCSPCAServiceRequest */                   
    public OCSPCAServiceRequest(OCSPReq req, ArrayList<OCSPResponseItem> responseList, Extensions exts, String sigAlg, boolean includeChain) {
        this.req = req;
        this.responseList = responseList;
        this.exts = exts;
        this.sigAlg = sigAlg;       
        this.includeChain = includeChain;
    }
    public OCSPReq getOCSPrequest() {
        return req;
    }  
    public Extensions getExtensions() {
    	return exts;
    }
    public ArrayList<OCSPResponseItem> getResponseList() {
    	return responseList;
    }
    public String getSigAlg() {
        return sigAlg;
    }

    /** If true, the CA certificate chain is included in the response.
     * 
     * @return true if the CA cert chain should be included in the response.
     */
    public boolean includeChain() {
        return includeChain;
    }
    /** Used when the CA passes a certificate chain for use when signing with the CAs signature key
     * 
     * @return List with certificates or null, if another chain should be used
     */
	public List<Certificate> getCertificateChain() {
		return certificateChain;
	}
	public void setCertificateChain(List<Certificate> certificatechain) {
		this.certificateChain = certificatechain;
	}
    /** Used when the CA passes a private key (reference) for use when signing with the CAs signature key
     * 
     * @return private key or null, if another private key should be used
     */
	public PrivateKey getPrivKey() {
		return privKey;
	}
	public void setPrivKey(PrivateKey privKey) {
		this.privKey = privKey;
	}
	public String getPrivKeyProvider() {
		return privKeyProvider;
	}
	public void setPrivKeyProvider(String privKeyProvider) {
		this.privKeyProvider = privKeyProvider;
	}
	/** ResponderIdType from OCSPUtil.RESPONDERIDTYPE_NAME or KEYHASH
	 */
	public int getRespIdType() {
		return respIdType;
	}
	/** ResponderIdType from OCSPUtil.RESPONDERIDTYPE_NAME or KEYHASH
	 */
	public void setRespIdType(int respIdType) {
		this.respIdType = respIdType;
	}
	@Override
	public int getServiceType() {
		return ExtendedCAServiceTypes.TYPE_OCSPEXTENDEDSERVICE;
	}

}