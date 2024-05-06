import React, { useEffect, useState } from "react";
import "./App.css";
import {
  Container,
  Text,
  Title,
  Table,
  Space,
  Button,
  Modal,
} from "@mantine/core";
import { genericFetch } from "./fetch_utils";

import { Plant, BasketItem, Order } from "./interfaces";
import { AddItemToBasket } from "./AddItemToBasket";
import { useDisclosure } from "@mantine/hooks";
import { PayModal } from "./PayModal";

const statusMap: { [name: string]: string } = {
  AWAITING_PAYMENT: "Awaiting payment",
  ORDER_PLACED: "Order placed",
  ORDER_CANCELLED: "Order cancelled",
  ORDER_BEING_PREPARED: "Order being prepared",
  REFUNDING_ORDER: "Refunding order",
  ORDER_FINISHED: "Order completed",
};

function App() {
  const [plants, setPlants] = useState<Array<Plant>>([]);
  const [basket, setBasket] = useState<Array<BasketItem>>([]);
  const [orders, setOrders] = useState<Array<Order>>([]);
  const [currentOrder, setCurrentOrder] = useState<Order | null>(null);

  const [opened, { open, close }] = useDisclosure(false);

  const fetchPlants = () => {
    return genericFetch("plants", (data) => setPlants(data["plants"]));
  };

  const fetchOrders = () => {
    return genericFetch("orders", (data) => setOrders(data["orders"]));
  };

  const placeOrder = () => {
    return genericFetch("orders", () => {}, "POST", { items: basket }).then(
      () =>
        setTimeout(
          () =>
            fetchOrders()
              .then(fetchPlants)
              .then(() => setBasket([])),
          2000
        )
    );
  };

  const cancelOrder = (orderId: string) => {
    return genericFetch(`orders/${orderId}/cancel`, () => {}).then(() =>
      setTimeout(() => {
        fetchOrders();
        fetchPlants();
      }, 2000)
    );
  };

  const payForOrder = (orderId: string, cardDetails: any) => {
    return genericFetch(`payments/pay`, () => {}, "POST", {
      orderId: orderId,
      paymentCard: cardDetails,
    }).then(() =>
      setTimeout(() => {
        //po 2s frotend odpytuje znowu i dostaje nowy stan z backendu: complete: 
        fetchOrders();
        close();
      }, 2000)
    );
  };

  const removeFromBasket = (plantId: string) => {
    setBasket(basket.filter((it) => it.plantId !== plantId));
  };

  const calculateBasketCost = () => {
    return basket.reduce((prev, cur) => {
      return (
        prev +
        plants.filter((it) => it.id === cur.plantId)[0].price * cur.quantity
      );
    }, 0);
  };

  useEffect(() => {
    fetchPlants().then(fetchOrders);
  }, []);

  useEffect(() => {
    console.log(plants);
  }, [plants]);

  const addItem = (plant: Plant, quantity: number) => {
    setBasket(
      [...basket, { plantId: plant.id, quantity: quantity }]
        .sort((a, b) => (a.plantId > b.plantId ? 1 : -1))
        .reduce<BasketItem[]>((prev, cur) => {
          console.log("Prev: ", prev, "Cur: ", cur);
          if (prev.length === 0) {
            return [cur];
          }
          const last = prev[prev.length - 1];
          if (cur.plantId === last?.plantId) {
            prev[prev.length - 1].quantity += cur.quantity;
            return prev;
          } else {
            return [...prev, cur];
          }
        }, [])
    );
  };

  return (
    <div className="App">
      <PayModal
        opened={opened}
        close={close}
        currentOrder={currentOrder}
        payFunc={payForOrder}
      />
      <Container size="md" style={{ border: "1px solid #ccc" }}>
        <Title order={1}>Plants Shop</Title>
        <Space h="lg" />
        <Text size="xl">Your basket</Text>
        {basket.length > 0 ? (
          <>
            <Table>
              <Table.Thead>
                <Table.Tr>
                  <Table.Th>ID</Table.Th>
                  <Table.Th>Name</Table.Th>
                  <Table.Th>Price</Table.Th>
                  <Table.Th>Quantity</Table.Th>
                  <Table.Th>Actions</Table.Th>
                </Table.Tr>
              </Table.Thead>
              <Table.Tbody>
                {basket.map((bi, idx) => (
                  <Table.Tr key={bi.plantId}>
                    <Table.Td>{idx + 1}</Table.Td>
                    <Table.Td>
                      {plants.filter((it) => it.id === bi.plantId)[0].name}
                    </Table.Td>
                    <Table.Td>
                      {plants.filter((it) => it.id === bi.plantId)[0].price}
                    </Table.Td>
                    <Table.Td>{bi.quantity}</Table.Td>
                    <Table.Td>
                      <Button
                        color="red"
                        style={{ marginRight: "10px" }}
                        onClick={() => removeFromBasket(bi.plantId)}
                      >
                        Remove
                      </Button>
                    </Table.Td>
                  </Table.Tr>
                ))}
              </Table.Tbody>
            </Table>
            <Text>Total cost of your basket: {calculateBasketCost()} PLN</Text>
            <Button color="green" onClick={placeOrder}>
              Place order
            </Button>
          </>
        ) : (
          <Text>Your basket is empty. Add some plants to it!</Text>
        )}
        <Space h="lg" />
        <Text size="xl">Your orders</Text>
        {orders.length === 0 ? (
          <Text>You have no orders yet.</Text>
        ) : (
          <Table>
            <Table.Thead>
              <Table.Tr>
                <Table.Th>ID</Table.Th>
                <Table.Th>Items</Table.Th>
                <Table.Th>Total cost [PLN]</Table.Th>
                <Table.Th>Status</Table.Th>
                <Table.Th>Actions</Table.Th>
              </Table.Tr>
            </Table.Thead>
            <Table.Tbody>
              {orders.map((order, idx) => (
                <Table.Tr key={order.id}>
                  <Table.Td>{idx + 1}</Table.Td>
                  <Table.Td>
                    {order.items.item.map((it) => (
                      <Text size="xs">
                        {it.quantity}x{" "}
                        {plants.filter((p) => p.id === it.plantId)[0].name}
                      </Text>
                    ))}
                  </Table.Td>
                  <Table.Td>{order.totalCost}</Table.Td>
                  <Table.Td>{statusMap[order.status]}</Table.Td>
                  <Table.Td>
                    {order.status === "AWAITING_PAYMENT" && (
                      <Button
                        color="yellow"
                        style={{ marginRight: "10px" }}
                        onClick={() => {
                          setCurrentOrder(order);
                          open();
                        }}
                      >
                        Pay
                      </Button>
                    )}
                    {!["ORDER_FINISHED", "ORDER_CANCELLED", "REFUNDING_ORDER"].includes(
                      order.status
                    ) && (
                      <Button color="red" onClick={() => cancelOrder(order.id)}>
                        Cancel
                      </Button>
                    )}
                  </Table.Td>
                </Table.Tr>
              ))}
            </Table.Tbody>
          </Table>
        )}
        <Space h="lg" />
        <Text size="xl">Available plants</Text>
        <Table striped>
          <Table.Thead>
            <Table.Tr>
              <Table.Th>ID</Table.Th>
              <Table.Th>Name</Table.Th>
              <Table.Th>Price [PLN]</Table.Th>
              <Table.Th>Quantity</Table.Th>
              <Table.Th>Actions</Table.Th>
            </Table.Tr>
          </Table.Thead>
          <Table.Tbody>
            {plants.map((plant) => (
              <Table.Tr key={plant.id}>
                <Table.Td>{plant.id}</Table.Td>
                <Table.Td>{plant.name}</Table.Td>
                <Table.Td>{plant.price}</Table.Td>
                <Table.Td>{plant.quantity}</Table.Td>
                <Table.Td>
                  <AddItemToBasket plant={plant} addItem={addItem} />
                </Table.Td>
              </Table.Tr>
            ))}
          </Table.Tbody>
        </Table>
      </Container>
    </div>
  );
}

export default App;
