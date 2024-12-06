import {Category as CategoryType} from '@/types/Category';
import Link from 'next/link';
import Image from 'next/image'; // 修正：ImageWrapperではなくnext/imageを使用
import {getImageUrl} from "@/utils/getImageUrl";

interface UserCategoryTileProps {
    category: CategoryType;
}

const UserCategoryTile = ({category}: UserCategoryTileProps) => {
    return (
        <Link href={`/categories/${category.id}`}>
            <div
                className="bg-gray-800 rounded-lg shadow-md p-3 hover:shadow-lg transition-shadow duration-300 transform hover:-translate-y-1 cursor-pointer flex flex-col items-center"
                style={{width: "180px", height: "260px"}}
            >
                {/* 画像部分 */}
                <div className="w-full h-32 bg-gray-700 rounded-md overflow-hidden relative">
                    <Image
                        src={getImageUrl(category.image)}
                        alt={`${category.name}の画像`}
                        className="object-cover"
                        fill // fill を使用して親要素に画像を適応
                        sizes="(max-width: 180px) 100vw, 180px"
                        priority={true}
                    />
                </div>

                {/* カテゴリ名 */}
                <h3 className="text-base font-semibold text-white mt-2">{category.name}</h3>

                {/* 説明文 */}
                {category.description && (
                    <p className="text-xs text-gray-400 mt-1 text-center">{category.description}</p>
                )}
            </div>
        </Link>
    );
};

export default UserCategoryTile;
