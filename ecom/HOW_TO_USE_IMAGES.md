# How to Use Images for Rice Products

## Step 1: Add Image Column to Database

If your database already exists, run this SQL:

```sql
USE ecom;
ALTER TABLE riceproduct_data ADD COLUMN riceImageFile VARCHAR(255) NULL;
```

Or use the script: `ecom/add_image_column.sql`

## Step 2: Place Your Images

You have **3 options** for storing images:

### Option A: Store in Backend Upload Directory (Recommended)
1. Create or navigate to the `upload-dir` folder (one level up from your `ecommerce` folder)
2. Place your image files there (e.g., `basmati.jpg`, `jasmine.png`)
3. In the database, store just the filename: `basmati.jpg`

**Example:**
```
C:\Users\Evan\Desktop\
├── MARIPOSA\
│   ├── ecommerce\
│   └── ...
└── upload-dir\          ← Create this folder here
    ├── basmati.jpg
    ├── jasmine.png
    └── sinandomeng.jpg
```

### Option B: Store in Frontend Assets Folder
1. Place images in `frontend/frontend/src/assets/rice-images/`
2. In the database, store the path: `assets/rice-images/basmati.jpg`
3. Update the `getImageUrl()` method to handle assets path

### Option C: Use External URLs
1. Upload images to a CDN or image hosting service
2. In the database, store the full URL: `https://example.com/images/basmati.jpg`

## Step 3: Add Image Filenames to Database

### Method 1: Using MySQL Command Line
```sql
USE ecom;
UPDATE riceproduct_data SET riceImageFile = 'basmati.jpg' WHERE riceName = 'Basmati (Jordan Farms)';
UPDATE riceproduct_data SET riceImageFile = 'jasmine.png' WHERE riceName = 'Jasmine (Thai/Local)';
```

### Method 2: Using the API Endpoint
You can update via the `/api/riceproduct/{id}` endpoint:
```json
PUT http://localhost:8082/api/riceproduct/1
{
  "riceName": "Basmati (Jordan Farms)",
  "riceDescription": null,
  "riceQuantity": "1kg",
  "ricePrice": 105.00,
  "riceImageFile": "basmati.jpg"
}
```

### Method 3: Direct SQL Insert/Update
When inserting new records, include the image filename:
```sql
INSERT INTO riceproduct_data (riceName, riceDescription, riceQuantity, ricePrice, riceImageFile) 
VALUES ('Basmati (Jordan Farms)', NULL, '1kg', 105.00, 'basmati.jpg');
```

## Step 4: Restart Your Backend

After adding images, restart your Spring Boot application to pick up the new WebConfig for serving static files.

## Image URL Format

- **If stored in upload-dir**: `http://localhost:8082/uploads/{filename}`
  - Example: `basmati.jpg` → `http://localhost:8082/uploads/basmati.jpg`
  
- **If full URL**: Already works as-is
  - Example: `https://example.com/image.jpg`

## Tips

1. **Image File Naming**: Use lowercase, no spaces (e.g., `basmati-jordan-farms.jpg`)
2. **Supported Formats**: JPG, PNG, GIF, WebP
3. **Image Size**: Recommended max 2MB per image for fast loading
4. **Fallback**: If image doesn't load, it will show a placeholder or default rice bag image

