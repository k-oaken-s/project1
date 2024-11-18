CREATE TABLE user_tier (
    id UUID PRIMARY KEY,
    anonymous_id VARCHAR(255) NOT NULL,
    category_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE CASCADE
);

CREATE TABLE user_tier_levels (
    id UUID PRIMARY KEY,
    user_tier_id UUID NOT NULL,
    tier_name VARCHAR(255) NOT NULL,
    tier_order INT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user_tier FOREIGN KEY (user_tier_id) REFERENCES user_tier(id) ON DELETE CASCADE,
    UNIQUE (user_tier_id, tier_name),
    UNIQUE (user_tier_id, tier_order)
);

CREATE TABLE user_tier_items (
    id UUID PRIMARY KEY,
    user_tier_id UUID NOT NULL,
    item_id UUID NOT NULL,
    tier_config_id UUID NOT NULL,
    "order" INT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user_tier FOREIGN KEY (user_tier_id) REFERENCES user_tier(id) ON DELETE CASCADE,
    CONSTRAINT fk_tier_config FOREIGN KEY (tier_config_id) REFERENCES tier_config(id) ON DELETE CASCADE,
    CONSTRAINT fk_item FOREIGN KEY (item_id) REFERENCES item(id) ON DELETE CASCADE,
    UNIQUE (tier_config_id, "order")
);
