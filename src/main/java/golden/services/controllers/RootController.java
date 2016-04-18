/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package golden.services.controllers;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author csiqueira
 */
@RestController
@RequestMapping("/")
public class RootController implements ErrorController {
	@RequestMapping("/error")
	public String error() {
		return null;
	}
	
	@Override
    public String getErrorPath() {
        return "/error";
    }
}
