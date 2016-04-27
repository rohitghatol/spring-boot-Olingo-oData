/**
 * 
 */
package com.rohitghatol.spring.odata.controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.olingo.commons.api.http.HttpHeader;
import org.apache.olingo.commons.api.http.HttpMethod;
import org.apache.olingo.server.api.*;
import org.apache.olingo.server.api.edm.provider.EdmProvider;
import org.apache.olingo.server.api.edmx.EdmxReference;
import org.apache.olingo.server.api.processor.EntityCollectionProcessor;
import org.apache.olingo.server.api.serializer.SerializerException;
import org.apache.olingo.server.core.ODataHandler;
import org.apache.olingo.server.core.ODataHandlerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rohitghatol.spring.odata.exception.EdmException;

// TODO: Auto-generated Javadoc
/**
 * The Class EDMController.
 *
 * @author rohitghatol
 */
@RestController
@RequestMapping(EDMController.URI)
public class EDMController {

	public static final String URI = "/odata";

	/** The edm provider. */
	@Autowired
	private EdmProvider edmProvider;

	/** The enity collection processor. */
	@Autowired
	private EntityCollectionProcessor enityCollectionProcessor;


	/**
	 * Process.
	 *
	 * @param request the req
	 * @param response the Http response
	 */
	@RequestMapping(value = "*")
	public void process(HttpServletRequest request, HttpServletResponse response) {
		OData odata = OData.newInstance();
		ServiceMetadata edm = odata.createServiceMetadata(edmProvider,
				new ArrayList<EdmxReference>());
		ODataHttpHandler handler = odata.createHandler(edm);
		handler.register(enityCollectionProcessor);
		handler.process(new HttpServletRequestWrapper(request) {
			// Spring MVC matches the whole path as the servlet path
			// Olingo wants just the prefix, ie upto /odata, so that it
			// can parse the rest of it as an OData path. So we need to override
			// getServletPath()
			@Override
			public String getServletPath() {
				return EDMController.URI;
			}
		}, response);
	}


}
