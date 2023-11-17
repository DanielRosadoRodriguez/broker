package app.server.model;

import java.io.File;

public class AdministradorProductoServer {
    private Producto producto1;
    private Producto producto2;
    private Producto producto3;
    
    public AdministradorProductoServer(File urlProducto1, File urlProducto2, File urlProducto3){
        this.producto1 = new Producto(urlProducto1);
        this.producto2 = new Producto(urlProducto2);
        this.producto3 = new Producto(urlProducto3);
    }
    

    public Producto getProducto1() {
        return producto1;
    }

    public void setProducto1(Producto producto1) {
        this.producto1 = producto1;
    }

    public Producto getProducto2() {
        return producto2;
    }

    public void setProducto2(Producto producto2) {
        this.producto2 = producto2;
    }

    public Producto getProducto3() {
        return producto3;
    }

    public void setProducto3(Producto producto3) {
        this.producto3 = producto3;
    }
    
    
}
