// Implement the methods in PizzaService for Task 3
// Add appropriate parameter and return type 

import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { firstValueFrom } from "rxjs";
import { Order } from "./models";

@Injectable()
export class PizzaService {

  constructor(private http: HttpClient) { }

  // POST /api/order
  // Add any required parameters or return type
  createOrder(order: Order) { 
    return firstValueFrom(
      this.http.post<Response>('/api/order', order)
    )
  }

  // GET /api/order/<email>/all
  // Add any required parameters or return type
  getOrders(email: string) {
    return firstValueFrom(
      this.http.get<any>(`/api/order/${email}/all`)
    )
  }

}
