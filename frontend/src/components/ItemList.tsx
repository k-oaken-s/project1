import { Item } from '@/types/Category';

interface ItemListProps {
    items: Item[];
    onEdit: (item: Item) => void;
}

const ItemList = ({ items, onEdit }: ItemListProps) => (
    <ul className="space-y-4">
        {items.map((item) => (
            <li key={item.id} className="border rounded p-4 shadow-md flex items-center">
                {item.image && (
                    <img
                        src={`data:image/png;base64,${item.image}`}
                        alt={`${item.name}の画像`}
                        className="w-16 h-16 object-cover mr-4"
                        loading="lazy"
                    />
                )}
                <span className="font-semibold text-lg flex-1">{item.name}</span>
                <button onClick={() => onEdit(item)} className="bg-yellow-500 text-white py-1 px-3 rounded hover:bg-yellow-600 ml-2">
                    編集
                </button>
            </li>
        ))}
    </ul>
);

export default ItemList;
