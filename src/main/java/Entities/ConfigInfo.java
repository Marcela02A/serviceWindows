/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author Marcela
 */
public class ConfigInfo {

    private long frequencyTime;
    private String accessRoute;
    private String [] organizedDocExtension;

    /**
     * @return the frequencyTime
     */
    public long getFrequencyTime() {
        return frequencyTime * 60000;
    }

    /**
     * @param frequencyTime the frequencyTime to set
     */
    public void setFrequencyTime(long frequencyTime) {
        this.frequencyTime = frequencyTime;
    }

    /**
     * @return the accessRoute
     */
    public String getAccessRoute() {
        return accessRoute;
    }

    /**
     * @param accessRoute the accessRoute to set
     */
    public void setAccessRoute(String accessRoute) {
        this.accessRoute = accessRoute;
    }

    /**
     * @return the organizedDocExtension
     */
    public String[] getOrganizedDocExtension() {
        return organizedDocExtension;
    }

    /**
     * @param organizedDocExtension the organizedDocExtension to set
     */
    public void setOrganizedDocExtension(String[] organizedDocExtension) {
        this.organizedDocExtension = organizedDocExtension;
    }

}
