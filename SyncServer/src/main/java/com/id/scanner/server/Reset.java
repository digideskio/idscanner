package com.id.scanner.server;

public class Reset {

	public static void main(String[] args) {
        DatabaseAdapter db = new DatabaseAdapter();
        db.open();
        
        db.deleteTable();
        db.createTable();
        
        db.close();
    }

}
