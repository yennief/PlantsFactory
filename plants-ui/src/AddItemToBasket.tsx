import { Button, NumberInput, Space } from "@mantine/core";

import { Plant } from "./interfaces";
import { useState } from "react";

export const AddItemToBasket = ({
  plant,
  addItem,
}: {
  plant: Plant;
  addItem: (plant: Plant, quantity: number) => void;
}) => {
  const [value, setValue] = useState<number>(0);

  return (
    <>
      <NumberInput
        style={{ maxWidth: "70px" }}
        value={value}
        onChange={(n) =>
          setValue(
            typeof n == "string"
              ? Math.min(Number.parseInt(n), plant.quantity)
              : Math.min(n, plant.quantity)
          )
        }
      />
      <Space h="xs" />
      <Button onClick={() => addItem(plant, value)}>Add</Button>
    </>
  );
};
