import { Text, Modal, Input, Flex, Space, Button, Center } from "@mantine/core";
import { Order } from "./interfaces";
import { useState } from "react";

export const PayModal = ({
  opened,
  close,
  currentOrder,
  payFunc,
}: {
  opened: boolean;
  close: () => void;
  currentOrder: Order | null;
  payFunc: (orderid: string, card: any) => void;
}) => {
  const [cardDetails, setCardDetails] = useState({
    name: "",
    number: "",
    validTo: "",
    CVV2: "",
  });

  return (
    <Modal opened={opened} onClose={close} title={"Pay for the order"}>
      <Text>Order ID: {currentOrder?.id}</Text>
      <Space h="xs" />
      <Text>Total price: {currentOrder?.totalCost} PLN</Text>
      <Space h="xs" />
      <Input
        placeholder="Your name"
        value={cardDetails.name}
        onChange={(nv) => {
          setCardDetails({ ...cardDetails, name: nv.target.value });
        }}
      />
      <Space h="xs" />
      <Input
        placeholder="Card number"
        value={cardDetails.number}
        onChange={(nv) => {
          setCardDetails({ ...cardDetails, number: nv.target.value });
        }}
      />
      <Space h="xs" />
      <Flex>
        <Input
          placeholder="Valid to: (MM/YY)"
          value={cardDetails.validTo}
          onChange={(nv) => {
            setCardDetails({ ...cardDetails, validTo: nv.target.value });
          }}
        />
        <Space w="xs" />
        <Input
          placeholder="CVV2 security code"
          value={cardDetails.CVV2}
          onChange={(nv) => {
            setCardDetails({ ...cardDetails, CVV2: nv.target.value });
          }}
        />
      </Flex>
      <Space h="xs" />
      <Center>
        <Button
          color="green"
          disabled={Object.values(cardDetails).some((v) => v.length === 0)}
          onClick={() => payFunc(currentOrder?.id || "", cardDetails)}
        >
          Pay
        </Button>
      </Center>
    </Modal>
  );
};
