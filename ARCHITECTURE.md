by.tsiareshchanka.football
├── telegram/          # Telegram adapter: webhook, polls, commands
├── match/             # Match lifecycle: creation, scheduling, booking polls
├── squad/             # Team balancing and squad persistence
├── rating/            # RatingEngine SPI + manual implementation
├── player/            # Player profiles and ratings
├── storage/           # VideoStorage abstraction (S3 / local)
├── sharding/          # ShardRouter, virtual shard mapping
└── shared/            # Cross-cutting: events, idempotency, config
