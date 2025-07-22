# Visit Log Service

A lightweight Spring Boot microservice for securely logging website visits. Designed for integration with a personal or small-scale website, it provides a REST API that records visitor information into a PostgreSQL database (via Supabase) with IP-based rate limiting and CORS protection.

## Features

- REST API for tracking page visits
- Per-IP rate limiting using Bucket4j
- Configurable CORS policy for secure cross-origin requests
- Accurate client IP extraction behind reverse proxies (Cloudflare, Render)
- Dockerized for seamless deployment
- Health check endpoint for monitoring
- Supports production and development modes

## Tech Stack

- Java 17 with Spring Boot 3
- Bucket4j for token-bucket rate limiting
- Supabase (PostgreSQL) for data storage
- Docker for containerization
- Deployed on Render (free tier)

## How It Works

1. The website sends a POST request to `/track` with visitor metadata.
2. Service extracts the real client IP address by inspecting:
   - `X-Forwarded-For`
   - `CF-Connecting-IP`
   - `True-Client-IP`
   - Fallback to `request.getRemoteAddr()`
3. Checks rate limits (e.g., 20 requests per IP per 15 minutes).
4. Stores visit data:
   - IP address
   - Page visited
   - Device information
   - Timestamp

## API Endpoints

### POST /track

Logs a website visit.

**Request JSON:**
```json
{
  "ip_address": "99.46.126.226",
  "page_visited": "/about",
  "device_info": "Chrome on macOS"
}
```

**Response JSON:**
```json
{
  "success": true,
  "message": "Visit recorded successfully"
}
```

### GET /health

Simple health check for uptime monitoring.

## Security & Rate Limiting

- Uses Bucket4j for IP-based token bucket rate limiting.
- Default limit: 20 requests per 15 minutes per IP.
- CORS configuration allows only specified origins in production.
- Requests missing an Origin header are blocked unless DEV_MODE is enabled.

## Local Development

### Requirements

- Java 17
- Maven
- Docker (optional)

### Run Locally

```bash
# Build and run
mvn spring-boot:run

# Or use Docker
docker build -t visit-log-service .
docker run -p 8080:8080 visit-log-service
```

### Environment Variables

- `SUPABASE_URL`: Supabase REST API URL
- `SUPABASE_KEY`: Supabase API Key
- `ALLOWED_HOSTS`: Comma-separated allowed origins
- `DEV_MODE`: true/false (enables relaxed CORS in development)
- `PORT`: Port to bind (Render uses PORT)

## Deployment

This service runs in Docker and is deployed to Render.

### Deployment Steps

1. Push repository to GitHub.
2. Connect GitHub repo to Render.
3. Select Docker as environment.
4. Set environment variables in Render dashboard.
5. Deploy.

**Example `.env` file for local testing:**
```dotenv
SUPABASE_URL=https://xyzcompany.supabase.co/rest/v1
SUPABASE_KEY=your-supabase-api-key
ALLOWED_HOSTS=https://www.yourdomain.com
DEV_MODE=true
PORT=8080
```