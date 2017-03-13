package com.rightandabove.controllers;

import com.google.gson.Gson;
import com.rightandabove.models.UploadedFile;
import com.rightandabove.models.XMLUtil;
import com.rightandabove.utils.validators.FileValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

/**
 * Created by alex on 11.03.17.
 */
@Controller
public class FileController {

    private static final Logger logger = Logger.getLogger(FileController.class);

    @Autowired
    FileValidator fileValidator;

    @Autowired
    XMLUtil xmlUtil;


    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView uploadFile(@ModelAttribute("uploadedFile") UploadedFile uploadedFile, BindingResult bindingResult)
            throws IOException, ParserConfigurationException, SAXException {

        String filename = null;
        fileValidator.validate(uploadedFile, bindingResult);

        final MultipartFile file = uploadedFile.getFile();
        final ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("upload_file");
            String json = new Gson().toJson(xmlUtil.getCDList("/home/alex/Desktop/text_doc.xml") );

            modelAndView.addObject("cdList", json);
        } else {
            byte[] bytes = file.getBytes();

            filename = file.getOriginalFilename();

            String rootPath = System.getProperty("catalina.home");
            File dir = new File(rootPath + File.separator + "tmpFiles");

            logger.info(dir.toString());

            if (!dir.exists()) {
                dir.mkdirs();
            }

            File tempFile = new File(dir.getAbsolutePath() + File.separator + filename);

            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(tempFile));
            stream.write(bytes);
            stream.flush();
            stream.close();

            logger.info("uploaded: " + tempFile.getAbsolutePath());

            xmlUtil.addCdList(xmlUtil.getCDList(uploadedFile.getFile().getInputStream()));

            RedirectView redirectView = new RedirectView("/");
            redirectView.setStatusCode(HttpStatus.FOUND);
            modelAndView.setView(redirectView);

        }

        return modelAndView;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView uploadFile() throws ParserConfigurationException, SAXException, IOException {
        final ModelAndView modelAndView = new ModelAndView("upload_file");

        String json = new Gson().toJson(xmlUtil.getCDList("/home/alex/Desktop/text_doc.xml") );

        modelAndView.addObject("cdList", json);

        return modelAndView;
    }

    @RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> downloadStuff()
            throws IOException {
        File file = new File("/home/alex/Desktop/text_doc.xml");

        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentType(MediaType.APPLICATION_XML);
        respHeaders.setContentDispositionFormData("attachment", "text_doc.xml");

        InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
        return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);
    }



}
