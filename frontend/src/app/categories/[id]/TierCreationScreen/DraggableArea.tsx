import {Item} from "@/types/Item";
import {useDroppable} from "@dnd-kit/core";
import React from "react";

type DroppableAreaProps = {
    id: string;
    items: Item[];
    children: React.ReactNode;
};

const DroppableArea: React.FC<DroppableAreaProps> = ({id, items, children}) => {
    const {setNodeRef} = useDroppable({
        id,
        data: {tierName: id},
    });

    return (
        <div
            id={id}
            className="relative flex flex-col gap-4 p-4"
            style={{
                backgroundColor: "inherit", // 親要素の背景色を引き継ぐ
                borderRadius: "8px",
            }}
        >
            {children}
        </div>

    );
};

export default DroppableArea;
