import { Category as CategoryType } from '@/types/Category';
import { Col, Row } from 'antd';
import Category from './Category';

interface CategoryListProps {
    categories: CategoryType[];
}

const CategoryList = ({ categories }: CategoryListProps) => (
    <Row gutter={[16, 16]}>
        {categories.length > 0 ? (
            categories.map((category) => (
                <Col
                    key={category.id}
                    xs={24}
                    sm={12}
                    md={8}
                    lg={6}
                    style={{ height: '100%' }} // 高さを100%に設定
                >
                    <Category category={category} />
                </Col>
            ))
        ) : (
            <Col span={24} className="text-gray-500 text-center">
                カテゴリーがありません
            </Col>
        )}
    </Row>
);

export default CategoryList;
