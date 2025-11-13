# Using Images from resources/rice-pics Folder

## Your Image Location
Images are stored in: `C:\Users\Evan\Desktop\MARIPOSA\resources\rice-pics\`

## How to Update Database

### Option 1: Store just the filename
If your images are named like `basmati.jpg`, `jasmine.png`, etc., store just the filename:

```sql
USE ecom;
UPDATE riceproduct_data 
SET riceImageFile = 'basmati.jpg' 
WHERE riceName = 'Basmati (Jordan Farms)';

UPDATE riceproduct_data 
SET riceImageFile = 'jasmine.png' 
WHERE riceName = 'Jasmine (Thai/Local)';
```

### Option 2: Store with subfolder path
If you want to organize images in subfolders, you can store the path:

```sql
UPDATE riceproduct_data 
SET riceImageFile = 'rice/basmati.jpg' 
WHERE riceName = 'Basmati (Jordan Farms)';
```

## Image URL Format

Images will be accessible at:
- `http://localhost:8082/images/basmati.jpg`
- `http://localhost:8082/images/rice/basmati.jpg` (if using subfolder)

## Steps to Make Images Work

1. **Restart your Spring Boot backend** (to load the new WebConfig)
2. **Update database** with image filenames (see SQL above)
3. **Check browser console** (F12) - you should see console.log messages showing the image URLs
4. **Test image URL directly** - Open `http://localhost:8082/images/your-image-name.jpg` in browser

## Troubleshooting

- **Images still not showing?**
  - Check browser console (F12) for 404 errors
  - Verify image filename in database matches actual filename in folder
  - Make sure backend is restarted after configuration changes
  - Test image URL directly: `http://localhost:8082/images/filename.jpg`

- **Check database has image paths:**
  ```sql
  SELECT riceID, riceName, riceImageFile FROM riceproduct_data WHERE riceImageFile IS NOT NULL;
  ```

