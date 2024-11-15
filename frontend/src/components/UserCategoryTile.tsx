"use client";

import { Category as CategoryType } from '@/types/Category';
import Link from 'next/link';
import { getImageUrl } from '@/utils/getImageUrl';

interface UserCategoryTileProps {
    category: CategoryType;
}

const UserCategoryTile = ({ category }: UserCategoryTileProps) => {
    return (
        <Link href={`/categories/${category.id}`}>
            <div className="bg-white rounded-lg shadow-md p-4 hover:shadow-lg transition-shadow duration-200 cursor-pointer">
                <img
                    src={getImageUrl(category.image)}
                    alt={`${category.name}の画像`}
                    className="w-full h-32 object-cover rounded-md mb-3"
                    loading="lazy"
                />
                <h3 className="text-lg font-semibold text-gray-800">{category.name}</h3>
                {category.description && <p className="text-sm text-gray-600 mt-2">{category.description}</p>}
            </div>
        </Link>
    );
};

export default UserCategoryTile;
