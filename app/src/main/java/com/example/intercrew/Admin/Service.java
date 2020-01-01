package com.example.intercrew.Admin;

import androidx.annotation.NonNull;

public class Service {
   private String _id;
   private String _servicename;
   private String _role;

   public Service() {
   }
   public Service(String id, String servicename, String role) {
      this._id = id;
      this._servicename = servicename;
      this._role = role;
   }
   public Service(String servicename, String role) {
      _servicename = servicename;
      _role = role;
   }

   public void setID(String id) {
      _id = id;
   }
   public String getID() {
      return _id;
   }
   public void setServiceName(String servicename) {
      _servicename = servicename;
   }
   public String getServiceName() {
      return _servicename;
   }
   public void setRole(String role) {
      _role = role;
   }
   public String getRole() {
      return _role;
   }


   @NonNull
   @Override
   public String toString() {
      return _servicename;
   }
}
