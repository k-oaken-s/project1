import { Category as CategoryType } from '@/types/Category';
import Category from './Category';

interface CategoryListProps {
    categories: CategoryType[];
}

const CategoryList = ({ categories }: CategoryListProps) => (
    <ul className="space-y-4">
        {categories.length > 0 ? (
            categories.map((category) => (
                <Category key={category.id} category={category} />
            ))
        ) : (
            <li className="text-gray-500">カテゴリーがありません</li>
        )}
    </ul>
);

export default CategoryList;
