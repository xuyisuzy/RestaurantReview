/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;


@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @PostConstruct
    public void postConstruct()
    {
        
    }
}
