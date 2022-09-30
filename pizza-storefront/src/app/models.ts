// Add your models here if you have any
export interface Order {
    name: string
    email: string
    size: number
    base: string
    sauce: string
    toppings: []
    comments: string
}

export interface OrderSummary {
    orderId: number
    name: string
    email: string
    amount: number
}

export interface Response {
    code: number
    message?: string
    data?: Order
}