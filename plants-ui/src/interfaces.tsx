export interface Plant {
  id: string;
  name: string;
  price: number;
  quantity: number;
}

export interface BasketItem {
  plantId: string;
  quantity: number;
}

export interface Order {
  id: string;
  totalCost: number;
  items: {
    item: Array<BasketItem>;
  };
  status: string;
}

export interface OrderRequest {
    items: Array<BasketItem>;
}
