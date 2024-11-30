import {Category as CategoryType} from '@/types/Category';
import Link from 'next/link';
import ImageWrapper from "@/components/ImageWrapper";
import {getImageUrl} from "@/utils/getImageUrl";

interface UserCategoryTileProps {
    category: CategoryType;
}

const UserCategoryTile = ({category}: UserCategoryTileProps) => {
    return (
        <Link href={`/categories/${category.id}`}>
            <div
                className="bg-white rounded-xl shadow-md p-4 hover:shadow-xl transition-shadow duration-300 transform hover:-translate-y-1 cursor-pointer">
                <ImageWrapper
                    src={getImageUrl(category.image)}
                    alt={`${category.name}の画像`}
                    className="w-full h-40 object-cover rounded-md mb-4"
                    loading="lazy"
                    layout="responsive"
                    width={400}
                    height={400}
                />
                <h3 className="text-xl font-semibold text-gray-900">{category.name}</h3>
                {category.description && <p className="text-sm text-gray-700 mt-2">{category.description}</p>}
            </div>
        </Link>
    );
};

export default UserCategoryTile;
