import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../../model/product.model';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private baseURL = "http://localhost:8080/api/smartphone"
  constructor(private httpClient: HttpClient) { }

  // Obtener todos los uproductos
  getProducts(): Observable<Product[]> {
    return this.httpClient.get<Product[]>(`${this.baseURL}/list`);
  }

  // Obtener un producto por ID
  getProductById(id: number): Observable<Product> {
    return this.httpClient.get<Product>(`${this.baseURL}/listId/${id}`);
  }

  // Crear un nuevo producto
  createProduct(product: Product): Observable<Product[]> {
    return this.httpClient.post<Product[]>(`${this.baseURL}/save`, product);
  }

  // Actualizar un producto
  updateProduct(id: number, product: Product): Observable<Product[]> {
    return this.httpClient.put<Product[]>(`${this.baseURL}/update/${id}`, product);
  }

  // Eliminar un producto
  deleteProduct(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.baseURL}/delete/${id}`);
  }
}

