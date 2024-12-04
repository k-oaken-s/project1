import React, {useState} from "react";
import {Item} from "@/types/Item";
import {Input, Typography} from "antd";
import DraggableItem from "./DraggableItem";
import DroppableArea from "./DraggableArea";

const {Title} = Typography;

type TierProps = {
    id: string;
    name: string;
    items: Item[];
    onNameChange: (newName: string) => void;
    backgroundColor: string; // 背景色を外部から受け取る
};

const Tier: React.FC<TierProps> = ({
                                       id,
                                       name,
                                       items,
                                       onNameChange,
                                       backgroundColor,
                                   }) => {
    const [isEditing, setIsEditing] = useState(false);
    const [currentName, setCurrentName] = useState(name);

    const handleSaveName = () => {
        if (currentName.trim()) {
            onNameChange(currentName);
        } else {
            setCurrentName(name); // 名前が空の場合、元に戻す
        }
        setIsEditing(false);
    };

    const handleCancelEdit = () => {
        setCurrentName(name); // 編集をキャンセルした場合、元の名前に戻す
        setIsEditing(false);
    };

    return (
        <div
            className="flex rounded-md shadow-md"
            style={{
                backgroundColor,
                overflow: "hidden", // エリア外にはみ出さないようにする
            }}
        >
            {/* 左側のTier名エリア */}
                <div
                    className="p-4"
                    style={{
                        flex: "0 0 200px",
                        borderRight: "1px solid #444",
                        display: "flex",
                        alignItems: "center",
                        justifyContent: "center",
                        backgroundColor: backgroundColor, // 背景色を維持
                    }}
                >
                    {isEditing ? (
                        <Input
                            value={currentName}
                            onChange={(e) => setCurrentName(e.target.value)}
                            onBlur={handleSaveName}
                            onPressEnter={handleSaveName}
                            onKeyDown={(e) => {
                                if (e.key === "Escape") handleCancelEdit();
                            }}
                            autoFocus
                            style={{
                                fontSize: "32px",
                                fontWeight: "300",
                                backgroundColor: backgroundColor,
                                color: "#E0E0E0",
                                borderRadius: "4px",
                                border: "none",
                                padding: "4px 8px",
                                width: "100%",
                            }}
                        />
                    ) : (
                        <Title
                            level={4}
                            className="text-white"
                            onClick={() => setIsEditing(true)}
                            style={{
                                cursor: "pointer",
                                margin: "0",
                                textAlign: "center",
                                width: "100%",
                                color: "#E0E0E0",
                                fontSize: "32px",
                                fontWeight: "300",
                            }}
                        >
                            {name}
                        </Title>
                    )}
                </div>


            {/* 右側のアイテムエリア */}
                <div
                    className="flex-1 p-4"
                    style={{
                        display: "flex",
                        flexDirection: "column",
                        gap: "0px", // エリア間の間隔を最小化
                        minHeight: "150px",
                    }}
                >
                    <DroppableArea id={id} items={items}>
                        <div className="flex flex-wrap gap-4">
                            {items.map((item) => (
                                <DraggableItem key={item.id} item={item}/>
                            ))}
                        </div>
                    </DroppableArea>
                </div>
        </div>
    );
};

export default Tier;
