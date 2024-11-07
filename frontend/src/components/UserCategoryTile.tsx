import { Category as CategoryType } from '@/types/Category';

interface UserCategoryTileProps {
    category: CategoryType;
}

const UserCategoryTile = ({ category }: UserCategoryTileProps) => (
    <div className="bg-white rounded-lg shadow-md p-4 hover:shadow-lg transition-shadow duration-200 cursor-pointer">
        {category.image && (
            <img
                src={`data:image/png;base64,${category.image}`}
                alt={`${category.name}の画像`}
                className="w-full h-32 object-cover rounded-md mb-3"
                loading="lazy"
            />
        )}
        <h3 className="text-lg font-semibold text-gray-800">{category.name}</h3>
        {category.description && <p className="text-sm text-gray-600 mt-2">{category.description}</p>}
    </div>
);

export default UserCategoryTile;
