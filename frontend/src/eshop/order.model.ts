export interface Order{
    clientId:string,
    items:Item[]
    
}

export interface Item{
    productId:string,
    quantity:number
}