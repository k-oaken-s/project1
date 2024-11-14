import { Category as CategoryType } from '@/types/Category';
import { Card } from 'antd';
import Link from 'next/link';

interface CategoryProps {
    category: CategoryType;
}

const Category = ({ category }: CategoryProps) => (
    <Link href={`/admin/categories/${category.id}`} passHref>
        <Card
            hoverable
            style={{
                height: '100%', // カードの高さを100%に設定
                display: 'flex',
                flexDirection: 'column',
                padding: 0, // カード全体のレイアウトを調整
            }}
            cover={
                <div style={{ height: 250, overflow: 'hidden' }}>
                    <img
                        src={category.image ? `data:image/png;base64,${category.image}` : '/default-thumbnail.jpg'}
                        alt={`${category.name}の画像`}
                        style={{ width: '100%', height: '100%', objectFit: 'cover' }}
                        onError={(e) => {
                            e.currentTarget.src = '/default-thumbnail.jpg';
                        }}
                    />
                </div>
            }
        >
            <div style={{ padding: '16px', flexGrow: 1, display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
                <span style={{ fontWeight: 'bold', fontSize: '16px', textAlign: 'center' }}>{category.name}</span>
            </div>
        </Card>
    </Link>
);

export default Category;
