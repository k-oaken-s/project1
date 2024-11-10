import { Item } from '@/types/Item';

interface ItemListProps {
    items: Item[];
    onEdit?: (item: Item) => void;
}

const ItemList = ({ items, onEdit }: ItemListProps) => (
    <ul className="space-y-4">
        {items.length > 0 ? (
            items.map((item) => (
                <li key={item.id} className="border rounded p-4 shadow-md flex items-center">
                    {item.image && (
                        <img
                            src={`data:image/png;base64,${item.image}`}
                            alt={`${item.name}の画像`}
                            className="w-16 h-16 object-cover mr-4"
                            loading="lazy"
                        />
                    )}
                    <div className="flex-1">
                        <span className="font-semibold text-lg">{item.name}</span>
                    </div>
                    {onEdit && (
                        <button
                            onClick={() => onEdit(item)}
                            className="text-blue-600 hover:underline"
                        >
                            編集
                        </button>
                    )}
                </li>
            ))
        ) : (
            <li className="text-gray-500">アイテムがありません</li>
        )}
    </ul>
);

export default ItemList;
