import { Component, OnInit } from '@angular/core';
import { Form, FormBuilder } from '@angular/forms';

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

  orderForm!: Form

  pizzaSize = SIZES[0]

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
  }

  updateSize(size: string) {
    this.pizzaSize = SIZES[parseInt(size)]
  }

}
