import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { OrderSummary } from 'src/app/models';
import { PizzaService } from 'src/app/pizza.service';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {

  ordersSummary: OrderSummary[] = []
  email!: string

  constructor(private activatedRoute: ActivatedRoute, private pizzaSvc: PizzaService) { }

  ngOnInit(): void {
    this.email = this.activatedRoute.snapshot.params['email']
    this.listOrders()
  }

  listOrders() {
    this.pizzaSvc.getOrders(this.email)
      .then(result => {
        console.log('>>> result: ', result)
      }).catch(err => {
        console.error('>>> error: ', err);
      })
  }

}
