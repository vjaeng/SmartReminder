/*
 * Copyright (C) 2011 Markus Junginger, greenrobot (http://greenrobot.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.viberlabs.app.smartreminder;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Generates entities and DAOs for the example project DaoExample.
 * 
 * Run it as a Java application (not Android).
 * 
 * @author Markus
 */
public class EntitiesDaoGenerator {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(3, "com.viberlabs.app.smartreminder");

        addReminder(schema);
       // addCustomerOrder(schema);

        new DaoGenerator().generateAll(schema, "../FragmentStarter/src-gen");
    }

    private static void addReminder(Schema schema) {
        Entity reminder = schema.addEntity("Reminder");
        reminder.addIdProperty().autoincrement();
        reminder.addStringProperty("name").notNull();
        reminder.addStringProperty("type");
        reminder.addStringProperty("notes");
        reminder.addDateProperty("dueDate");
        reminder.addStringProperty("position");

        /*
            private Long id;
    private int type;

        private String name;
        private java.util.Date dueDate;
        private Integer dueTime;
        private Integer repeat;
        private String notes;
        private java.util.Date dateCreated;
        private int selectdFlag;
         */
    }

//    private static void addCustomerOrder(Schema schema) {
//        Entity customer = schema.addEntity("Customer");
//        customer.addIdProperty();
//        customer.addStringProperty("name").notNull();
//
//        Entity order = schema.addEntity("Order");
//        order.setTableName("ORDERS"); // "ORDER" is a reserved keyword
//        order.addIdProperty();
//        Property orderDate = order.addDateProperty("date").getProperty();
//        Property customerId = order.addLongProperty("customerId").notNull().getProperty();
//        order.addToOne(customer, customerId);
//
//        ToMany customerToOrders = customer.addToMany(order, customerId);
//        customerToOrders.setName("orders");
//        customerToOrders.orderAsc(orderDate);
//    }

}
