import { Item } from '@/types/Item';
import { useEffect, useState } from 'react';

interface ItemFormProps {
    item?: Item;
    onSubmit: (name: string, image: File | null | "remove") => void;
}

const ItemForm = ({ item, onSubmit }: ItemFormProps) => {
    const [itemName, setItemName] = useState(item?.name || '');
    const [itemImage, setItemImage] = useState<File | null | "remove">(null);

    useEffect(() => {
        setItemName(item?.name || '');
        setItemImage(null);
    }, [item]);

    const handleImageUpload = (e: React.ChangeEvent<HTMLInputElement>) => {
        if (e.target.files && e.target.files[0]) {
            setItemImage(e.target.files[0]);
        } else {
            setItemImage(null);
        }
    };

    const handleSubmit = () => {
        onSubmit(itemName, itemImage);
        setItemName('');
        setItemImage(null);
    };

    return (
        <div className="mt-6 p-4 border rounded shadow-md bg-gray-50">
            <input
                type="text"
                placeholder="アイテム名を入力"
                value={itemName}
                onChange={(e) => setItemName(e.target.value)}
                className="border rounded p-2 w-full mb-2"
            />
            <input type="file" onChange={handleImageUpload} className="mb-2" />
            <button onClick={() => setItemImage("remove")} className="bg-gray-500 text-white py-1 px-4 rounded hover:bg-gray-600 mb-2">
                画像を削除
            </button>
            <button onClick={handleSubmit} className="bg-green-500 text-white py-2 px-4 rounded hover:bg-green-600">
                保存
            </button>
        </div>
    );
};

export default ItemForm;
