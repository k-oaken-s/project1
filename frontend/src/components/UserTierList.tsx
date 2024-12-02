import React from 'react';
import {Tier as TierType} from '@/types/Tier';

interface UserTierListProps {
    tiers: TierType[];
}

const UserTierList: React.FC<UserTierListProps> = ({tiers}) => {
    return (
        <ul className="space-y-4">
            {tiers.map((tier) => (
                <li key={tier.id} className="border p-4 rounded shadow">
                    <h2 className="text-2xl font-bold">{tier.title}</h2>
                    <p>{tier.description}</p>
                    <p className="text-sm text-gray-500">
                        作成日時: {new Date(tier.createdAt).toLocaleString()}
                    </p>
                </li>
            ))}
        </ul>
    );
};

export default UserTierList;
