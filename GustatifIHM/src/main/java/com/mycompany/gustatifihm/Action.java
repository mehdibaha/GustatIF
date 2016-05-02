/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gustatifihm;

import service.ServiceMetier;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author tthibault
 */
public abstract class Action {
    ServiceMetier serviceMetier;
    public abstract void execute(HttpServletRequest request);
    public void setServiceMetier(ServiceMetier service)
    {
        this.serviceMetier = service;
    }
}
