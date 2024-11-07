import { Category as CategoryType } from '@/types/Category';
import Link from 'next/link';

interface CategoryProps {
    category: CategoryType;
}

const Category = ({ category }: CategoryProps) => (
    <Link href={`/admin/categories/${category.id}`} className="block">
        <li className="border rounded p-4 shadow-md flex items-center cursor-pointer hover:bg-gray-100 transition">
            {category.image && (
                <img
                    src={`data:image/png;base64,${category.image}`}
                    alt={`${category.name}の画像`}
                    className="w-16 h-16 object-cover mr-4"
                    loading="lazy"
                />
            )}
            <div className="flex-1">
                <span className="font-semibold text-lg">{category.name}</span>
            </div>
        </li>
    </Link>
);

export default Category;
