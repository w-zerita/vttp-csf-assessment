import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { Order } from 'src/app/models';
import { Router } from '@angular/router';
import { PizzaService } from 'src/app/pizza.service';

const SIZES: string[] = [
  "Personal - 6 inches",
  "Regular - 9 inches",
  "Large - 12 inches",
  "Extra Large - 15 inches"
]

const PizzaToppings: string[] = [
    'chicken', 'seafood', 'beef', 'vegetables',
    'cheese', 'arugula', 'pineapple'
]

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  orderForm!: FormGroup
  toppingsFormArray!: FormArray

  pizzaSize = SIZES[0]

  constructor(private fb: FormBuilder, private router: Router,
    private pizzaSvc: PizzaService) {}

  ngOnInit(): void {
    this.orderForm = this.createOrderForm()
    this.addCheckboxes()
  }

  updateSize(size: string) {
    this.pizzaSize = SIZES[parseInt(size)]
  }

  listOrders() {
    const email = this.orderForm.value.email
    this.router.navigate(['/orders', email])
  }

  private addCheckboxes() {
    PizzaToppings.forEach(() => 
      this.toppingsFormArray.push(new FormControl(false)))
  }

  processOrder() {
    const order = this.orderForm.value as Order
    const selectedToppings = this.orderForm.value.toppings
      .map((checked: boolean, i: number) => checked? PizzaToppings[i] : null)
      .filter((v: any) => v != null)
    console.log('>>> selected toppings: ', selectedToppings);
    order.toppings = selectedToppings
    console.log(">>> order: ", order);

    this.pizzaSvc.createOrder(order)
      .then(result => {
        console.log('>>> result: ', result)
      }).catch(err => {
        console.error('>>. error: ', err)
      })
  }

  createOrderForm() {
    this.toppingsFormArray = this.fb.array([], [Validators.min(1)])
    return this.fb.group({
      name: this.fb.control<string>('', [Validators.required]),
      email: this.fb.control<string>('', [Validators.required, Validators.email]),
      size: this.fb.control<number>(0, [Validators.required]),
      base: this.fb.control<string>('', [Validators.required]),
      sauce: this.fb.control<string>('classic', [Validators.required]),
      toppings: this.toppingsFormArray,
      comments: this.fb.control<string>('')
    })
  }
}
