package com.id.scanner.server;

public class Reset {
    private static int port = 1212;

	public static void main(String[] args) {
        DatabaseAdapter db = new DatabaseAdapter();
        db.open();
        
        db.deleteTable();
        db.createTable();
        
        db.close();
    }

}
