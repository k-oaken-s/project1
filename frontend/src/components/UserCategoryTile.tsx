import {Category as CategoryType} from '@/types/Category';
import Link from 'next/link';
import Image from 'next/image';
import {getImageUrl} from "@/utils/getImageUrl";

interface UserCategoryTileProps {
    category: CategoryType;
}

const UserCategoryTile = ({category}: UserCategoryTileProps) => {
    return (
        <Link href={`/categories/${category.id}`}>
            <div
                className="bg-gray-800 rounded-md shadow-sm p-3 hover:shadow-lg transition-shadow duration-200 transform hover:-translate-y-1 cursor-pointer flex flex-col items-center"
                style={{width: "300px", height: "250px"}}
            >
                {/* 画像部分 */}
                <div className="w-full h-40 bg-gray-700 rounded-md overflow-hidden relative">
                    <Image
                        src={getImageUrl(category.image)}
                        alt={`${category.name}の画像`}
                        className="object-cover"
                        fill
                        sizes="(max-width: 300px) 100vw, 300px"
                        priority={true}
                    />
                </div>

                {/* カテゴリ名 */}
                <h3 className="text-base font-medium text-white mt-2">{category.name}</h3>

                {/* 説明文 */}
                {category.description && (
                    <p className="text-sm text-gray-400 mt-2 text-center">{category.description}</p>
                )}
            </div>
        </Link>
    );
};

export default UserCategoryTile;
