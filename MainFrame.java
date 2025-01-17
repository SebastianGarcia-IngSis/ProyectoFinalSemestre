/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyectofinalsis;

/**
 *
 * @author Sebastian
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Categoria {
    private String nombre;
    private String descripcion;

    public Categoria(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

class Producto {
    private String nombre;
    private double precio;
    private Categoria categoria;
    private int stock;

    public Producto(String nombre, double precio, Categoria categoria, int stock) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.stock = stock;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}

class CategoriaManager {
    private List<Categoria> categorias;

    public CategoriaManager() {
        categorias = new ArrayList<>();
        cargarCategorias();
    }

    public void agregarCategoria(Categoria categoria) {
        categorias.add(categoria);
        guardarCategorias();
    }

    public void modificarCategoria(int index, Categoria nuevaCategoria) {
        if (index >= 0 && index < categorias.size()) {
            categorias.set(index, nuevaCategoria);
            guardarCategorias();
        }
    }

    public void eliminarCategoria(int index) {
        if (index >= 0 && index < categorias.size()) {
            categorias.remove(index);
            guardarCategorias();
        }
    }

    public List<Categoria> listarCategorias() {
        return categorias;
    }

    private void guardarCategorias() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("categorias.txt"))) {
            for (Categoria categoria : categorias) {
                writer.write(categoria.getNombre() + "," + categoria.getDescripcion());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarCategorias() {
        try (BufferedReader reader = new BufferedReader(new FileReader("categorias.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    agregarCategoria(new Categoria(parts[0], parts[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportarCategorias() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("categorias_exportadas.csv"))) {
            writer.write("Nombre,Descripción");
            writer.newLine();
            for (Categoria categoria : categorias) {
                writer.write(categoria.getNombre() + "," + categoria.getDescripcion());
                writer.newLine();
            }
            JOptionPane.showMessageDialog(null, "Categorías exportadas a categorias_exportadas.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void importarCategorias() {
        try (BufferedReader reader = new BufferedReader(new FileReader("categorias_importadas.csv"))) {
            String line;
            reader.readLine(); // Salta la cabecera
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    agregarCategoria(new Categoria(parts[0], parts[1]));
                }
            }
            JOptionPane.showMessageDialog(null, "Categorías importadas desde categorias_importadas.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ProductoManager {
    private List<Producto> productos;

    public ProductoManager() {
        productos = new ArrayList<>();
        cargarProductos();
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
        guardarProductos();
    }

    public void modificarProducto(int index, Producto nuevoProducto) {
        if (index >= 0 && index < productos.size()) {
            productos.set(index, nuevoProducto);
            guardarProductos();
        }
    }

    public void eliminarProducto(int index) {
        if (index >= 0 && index < productos.size()) {
            productos.remove(index);
            guardarProductos();
        }
    }

    public List<Producto> listarProductos() {
        return productos;
    }

    public void actualizarStock(String nombre, int cantidad) {
        for (Producto producto : productos) {
            if (producto.getNombre().equalsIgnoreCase(nombre)) {
                producto.setStock(producto.getStock() + cantidad);
                guardarProductos();
                break;
            }
        }
    }

    private void guardarProductos() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("productos.txt"))) {
            for (Producto producto : productos) {
                writer.write(producto.getNombre() + "," + producto.getPrecio() + "," + producto.getCategoria().getNombre() + "," + producto.getStock());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarProductos() {
        try (BufferedReader reader = new BufferedReader(new FileReader("productos.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    Categoria categoria = new Categoria(parts[2], ""); // Descripción no se carga aquí
                    agregarProducto(new Producto(parts[0], Double.parseDouble(parts[1]), categoria, Integer.parseInt(parts[3])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportarProductos() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("productos_exportados.csv"))) {
            writer.write("Nombre,Precio,Categoría,Stock");
            writer.newLine();
            for (Producto producto : productos) {
                writer.write(producto.getNombre() + "," + producto.getPrecio() + "," + producto.getCategoria().getNombre() + "," + producto.getStock());
                writer.newLine();
            }
            JOptionPane.showMessageDialog(null, "Productos exportados a productos_exportados.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void importarProductos() {
        try (BufferedReader reader = new BufferedReader(new FileReader("productos_importados.csv"))) {
            String line;
            reader.readLine(); // Salta la cabecera
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    Categoria categoria = new Categoria(parts[2], ""); // Descripción no se carga aquí
                    agregarProducto(new Producto(parts[0], Double.parseDouble(parts[1]), categoria, Integer.parseInt(parts[3])));
                }
            }
            JOptionPane.showMessageDialog(null, "Productos importados desde productos_importados.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// Clase para manejar órdenes de compra
class PedidoCompra {
    private String productoNombre;
    private int cantidad;
    private double total;

    public PedidoCompra(String productoNombre, int cantidad, double total) {
        this.productoNombre = productoNombre;
        this.cantidad = cantidad;
        this.total = total;
    }

    public String getProductoNombre() {
        return productoNombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getTotal() {
        return total;
    }
}

class PedidoManager {
    private List<PedidoCompra> pedidos;

    public PedidoManager() {
        pedidos = new ArrayList<>();
        cargarPedidos();
    }

    public void agregarPedido(PedidoCompra pedido) {
        pedidos.add(pedido);
        guardarPedidos();
    }

    public List<PedidoCompra> listarPedidos() {
        return pedidos;
    }

    private void guardarPedidos() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("pedidos.txt"))) {
            for (PedidoCompra pedido : pedidos) {
                writer.write(pedido.getProductoNombre() + "," + pedido.getCantidad() + "," + pedido.getTotal());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarPedidos() {
        try (BufferedReader reader = new BufferedReader(new FileReader("pedidos.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    agregarPedido(new PedidoCompra(parts[0], Integer.parseInt(parts[1]), Double.parseDouble(parts[2])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class MainFrame extends JFrame {
    private CategoriaManager categoriaManager;
    private ProductoManager productoManager;
    private PedidoManager pedidoManager; // Añadido para gestionar pedidos
    private JTable categoriaTable;
    private JTable productoTable;
    private JTable pedidoTable; // Añadido para mostrar pedidos
    private DefaultTableModel categoriaTableModel;
    private DefaultTableModel productoTableModel;
    private DefaultTableModel pedidoTableModel; // Añadido para gestionar pedidos

    public MainFrame() {
        setTitle("Gestión de Inventarios");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        categoriaManager = new CategoriaManager();
        productoManager = new ProductoManager();
        pedidoManager = new PedidoManager();

        // Panel de Categorías
        String[] categoriaColumnNames = {"Nombre", "Descripción"};
        categoriaTableModel = new DefaultTableModel(categoriaColumnNames, 0);
        categoriaTable = new JTable(categoriaTableModel);
        actualizarTablaCategorias();

        // Panel de Productos
        String[] productoColumnNames = {"Nombre", "Precio", "Categoría", "Stock"};
        productoTableModel = new DefaultTableModel(productoColumnNames, 0);
        productoTable = new JTable(productoTableModel);
        actualizarTablaProductos();

        // Panel de Pedidos
        String[] pedidoColumnNames = {"Producto", "Cantidad", "Total"};
        pedidoTableModel = new DefaultTableModel(pedidoColumnNames, 0);
        pedidoTable = new JTable(pedidoTableModel);
        actualizarTablaPedidos();

        // Panel de botones para Categorías
        JPanel categoriaButtonPanel = new JPanel();
        categoriaButtonPanel.setLayout(new GridLayout(1, 5)); // Cambia el número de columnas

        JButton btnAgregarCategoria = new JButton("Agregar Categoría");
        JButton btnModificarCategoria = new JButton("Modificar Categoría");
        JButton btnEliminarCategoria = new JButton("Eliminar Categoría");
        JButton btnExportarCategorias = new JButton("Exportar Categorías");
        JButton btnImportarCategorias = new JButton("Importar Categorías");

        // Lógica para agregar categoría
        btnAgregarCategoria.addActionListener(e -> {
            String nombre = JOptionPane.showInputDialog("Nombre de la categoría:");
            String descripcion = JOptionPane.showInputDialog("Descripción de la categoría:");
            if (nombre != null && descripcion != null) {
                categoriaManager.agregarCategoria(new Categoria(nombre, descripcion));
                actualizarTablaCategorias();
            }
        });

        // Lógica para modificar categoría
        btnModificarCategoria.addActionListener(e -> {
            int selectedRow = categoriaTable.getSelectedRow();
            if (selectedRow != -1) {
                String nombre = JOptionPane.showInputDialog("Nuevo nombre de la categoría:", categoriaTableModel.getValueAt(selectedRow, 0));
                String descripcion = JOptionPane.showInputDialog("Nueva descripción de la categoría:", categoriaTableModel.getValueAt(selectedRow, 1));
                if (nombre != null && descripcion != null) {
                    categoriaManager.modificarCategoria(selectedRow, new Categoria(nombre, descripcion));
                    actualizarTablaCategorias();
                }
            }
        });

        // Lógica para eliminar categoría
        btnEliminarCategoria.addActionListener(e -> {
            int selectedRow = categoriaTable.getSelectedRow();
            if (selectedRow != -1) {
                categoriaManager.eliminarCategoria(selectedRow);
                actualizarTablaCategorias();
            }
        });

        // Lógica para exportar categorías
        btnExportarCategorias.addActionListener(e -> categoriaManager.exportarCategorias());

        // Lógica para importar categorías
        btnImportarCategorias.addActionListener(e -> {
            categoriaManager.importarCategorias();
            actualizarTablaCategorias();
        });

        categoriaButtonPanel.add(btnAgregarCategoria);
        categoriaButtonPanel.add(btnModificarCategoria);
        categoriaButtonPanel.add(btnEliminarCategoria);
        categoriaButtonPanel.add(btnExportarCategorias); // Agregado
        categoriaButtonPanel.add(btnImportarCategorias); // Agregado

        // Panel de botones para Productos
        JPanel productoButtonPanel = new JPanel();
        productoButtonPanel.setLayout(new GridLayout(1, 5)); // Cambia el número de columnas

        JButton btnAgregarProducto = new JButton("Agregar Producto");
        JButton btnModificarProducto = new JButton("Modificar Producto");
        JButton btnEliminarProducto = new JButton("Eliminar Producto");
        JButton btnExportarProductos = new JButton("Exportar Productos");
        JButton btnImportarProductos = new JButton("Importar Productos");

        // Lógica para agregar producto
        btnAgregarProducto.addActionListener(e -> {
            String nombre = JOptionPane.showInputDialog("Nombre del producto:");
            String precioStr = JOptionPane.showInputDialog("Precio del producto:");
            String categoriaNombre = JOptionPane.showInputDialog("Categoría del producto:");
            String stockStr = JOptionPane.showInputDialog("Stock del producto:");
            if (nombre != null && precioStr != null && categoriaNombre != null && stockStr != null) {
                double precio = Double.parseDouble(precioStr);
                int stock = Integer.parseInt(stockStr);
                Categoria categoria = new Categoria(categoriaNombre, ""); // Descripción no se carga aquí
                productoManager.agregarProducto(new Producto(nombre, precio, categoria, stock));
                actualizarTablaProductos();
            }
        });

        // Lógica para modificar producto
        btnModificarProducto.addActionListener(e -> {
            int selectedRow = productoTable.getSelectedRow();
            if (selectedRow != -1) {
                String nombre = JOptionPane.showInputDialog("Nuevo nombre del producto:", productoTableModel.getValueAt(selectedRow, 0));
                String precioStr = JOptionPane.showInputDialog("Nuevo precio del producto:", productoTableModel.getValueAt(selectedRow, 1));
                String categoriaNombre = JOptionPane.showInputDialog("Nueva categoría del producto:", productoTableModel.getValueAt(selectedRow, 2));
                String stockStr = JOptionPane.showInputDialog("Nuevo stock del producto:", productoTableModel.getValueAt(selectedRow, 3));
                if (nombre != null && precioStr != null && categoriaNombre != null && stockStr != null) {
                    double precio = Double.parseDouble(precioStr);
                    int stock = Integer.parseInt(stockStr);
                    Categoria categoria = new Categoria(categoriaNombre, ""); // Descripción no se carga aquí
                    productoManager.modificarProducto(selectedRow, new Producto(nombre, precio, categoria, stock));
                    actualizarTablaProductos();
                }
            }
        });

        // Lógica para eliminar producto
        btnEliminarProducto.addActionListener(e -> {
            int selectedRow = productoTable.getSelectedRow();
            if (selectedRow != -1) {
                productoManager.eliminarProducto(selectedRow);
                actualizarTablaProductos();
            }
        });

        // Lógica para exportar productos
        btnExportarProductos.addActionListener(e -> productoManager.exportarProductos());

        // Lógica para importar productos
        btnImportarProductos.addActionListener(e -> {
            productoManager.importarProductos();
            actualizarTablaProductos();
        });

        productoButtonPanel.add(btnAgregarProducto);
        productoButtonPanel.add(btnModificarProducto);
        productoButtonPanel.add(btnEliminarProducto);
        productoButtonPanel.add(btnExportarProductos); // Agregado
        productoButtonPanel.add(btnImportarProductos); // Agregado

        // Panel de botones para Pedidos
        JPanel pedidoButtonPanel = new JPanel();
        pedidoButtonPanel.setLayout(new GridLayout(1, 2));

        JButton btnAgregarPedido = new JButton("Agregar Pedido");
        JButton btnListarPedidos = new JButton("Listar Pedidos");

        // Acción para agregar pedido
        btnAgregarPedido.addActionListener(e -> {
            String productoNombre = JOptionPane.showInputDialog("Nombre del Producto:");
            String cantidadStr = JOptionPane.showInputDialog("Cantidad:");
            if (productoNombre != null && cantidadStr != null) {
                int cantidad = Integer.parseInt(cantidadStr);
                double total = calcularTotal(productoNombre, cantidad);
                pedidoManager.agregarPedido(new PedidoCompra(productoNombre, cantidad, total));
                actualizarTablaPedidos();
            }
        });

        // Acción para listar pedidos
        btnListarPedidos.addActionListener(e -> actualizarTablaPedidos());

        // Añadir botones de pedidos
        pedidoButtonPanel.add(btnAgregarPedido);
        pedidoButtonPanel.add(btnListarPedidos);

        // Añadir paneles al frame
        add(categoriaButtonPanel, BorderLayout.NORTH);
        add(new JScrollPane(categoriaTable), BorderLayout.WEST);
        add(productoButtonPanel, BorderLayout.SOUTH);
        add(new JScrollPane(productoTable), BorderLayout.CENTER);
        
        // Añadir la tabla de pedidos al frame
        JPanel pedidoPanel = new JPanel(new BorderLayout());
        pedidoPanel.add(pedidoButtonPanel, BorderLayout.NORTH);
        pedidoPanel.add(new JScrollPane(pedidoTable), BorderLayout.CENTER);
        add(pedidoPanel, BorderLayout.EAST);

        // Iniciar la interfaz
        setVisible(true);
    }

    private void actualizarTablaCategorias() {
        categoriaTableModel.setRowCount(0);
        for (Categoria categoria : categoriaManager.listarCategorias()) {
            categoriaTableModel.addRow(new Object[]{categoria.getNombre(), categoria.getDescripcion()});
        }
    }

    private void actualizarTablaProductos() {
        productoTableModel.setRowCount(0);
        for (Producto producto : productoManager.listarProductos()) {
            productoTableModel.addRow(new Object[]{producto.getNombre(), producto.getPrecio(), producto.getCategoria().getNombre(), producto.getStock()});
        }
    }

    private void actualizarTablaPedidos() {
        pedidoTableModel.setRowCount(0);
        for (PedidoCompra pedido : pedidoManager.listarPedidos()) {
            pedidoTableModel.addRow(new Object[]{pedido.getProductoNombre(), pedido.getCantidad(), pedido.getTotal()});
        }
    }

    private double calcularTotal(String productoNombre, int cantidad) {
        for (Producto producto : productoManager.listarProductos()) {
            if (producto.getNombre().equalsIgnoreCase(productoNombre)) {
                return producto.getPrecio() * cantidad;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}
 