package dao;

import java.util.List;

import model_class.Product;

public interface ProductDao {

	public void createProduct(Product product);

	public Product readProductById(int id);

	public Product readProductByName(String name);

	public List<Product> readAllProducts();

	public void updateProduct(Product product);

	public String deleteProduct(int id);

	// public void printProduct();
	// public void printProduct(int id);
	// public void printProduct(String name);
}
