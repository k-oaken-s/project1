import {Category as CategoryType} from '@/types/Category';
import UserCategoryTile from './UserCategoryTile';

interface UserCategoryListProps {
    categories: CategoryType[];
}

const UserCategoryList = ({categories}: UserCategoryListProps) => (
    <div
        className="grid gap-4 p-4"
        style={{
            gridTemplateColumns: 'repeat(auto-fit, minmax(300px, 1fr))',
            placeItems: 'center',
        }}
    >
        {categories.map((category) => (
            <UserCategoryTile key={category.id} category={category}/>
        ))}
    </div>
);

export default UserCategoryList;
